package components.gameManager.ActionHandlers;

import components.board.*;
import components.board.presets.MapA;
import components.board.presets.MapB;
import components.cards.enums.Color;
import components.gameManager.IO.IOHandler;
import components.gameManager.IO.InfoString;
import components.player.Player;
import components.tokens.Token;
import javafx.application.Platform;
import lombok.Setter;
import ui.MapMovementHandler;

import java.util.List;
import java.util.Objects;

public class MovementHandler {
    public Map map;
    public MapHandler mapHandler;
    public IOHandler ioHandler;
    @Setter
    private boolean caveRules;

    public MovementHandler(){
        map = generateMap("A");
        mapHandler = new MapHandler(map);
        ioHandler = IOHandler.getInstance();
        caveRules = false;
    }

    public Map generateMap(String param){
        if(Objects.equals(param, "A")){
            MapA mapA = new MapA();
            return mapA.loadMap();
        }
        else if(Objects.equals(param, "B")){
            MapB mapB = new MapB();
            return mapB.loadMap();
        }
        else{
            return mapHandler.generateMapFromJar("src/main/java/components/board/serialization/");
        }

    }

    public void initializeMap(String param){
        map = generateMap(param);
        mapHandler = new MapHandler(map);
    }

    public void placePlayersAtStart(List<Player> players){
        List<Hexagon> startingHexagons = mapHandler.getStartingHexagons();

        for(int i = 0; i < players.size(); i++){
            players.get(i).setHexagon(startingHexagons.get(i));
            mapHandler.occupyHexagon(startingHexagons.get(i));
        }

        Platform.runLater(() -> {
            MapMovementHandler.updatePlayerInitialPosition(players, map);
        });
    }

    public void handleMovement(Player player){

        while(true){
            ioHandler.printMessage("Played card color: " + player.getPlayedCardColor());
            ioHandler.printMessage("Power left: " + player.getPower());

            MovementDirection movementDirection = chooseMovementDirection();
            if(movementDirection == null){
                break;
            }

            Hexagon playerInitialHexagon = player.getHexagon();
            Hexagon destinationHexagon = mapHandler.getHexagonInDirection(playerInitialHexagon,
                    movementDirection.ordinal());

            printDestinationHexagonInfo(destinationHexagon);

            boolean continueMoving = tryMovePlayer(player, destinationHexagon);
            if(!continueMoving){
                break;
            }
        }

    }

    private boolean tryMovePlayer(Player player, Hexagon destinationHexagon){
        boolean continueMoving = true;
        if(destinationHexagon == null){

            ioHandler.printMessage("You can't go outside the map");

        } else if (prohibitionByOccupancy(player, destinationHexagon)) {

            ioHandler.printMessage("The hexagon is occupied");

        } else if (isSacrificeCardHexagon(destinationHexagon)) {

            boolean isBlockade = destinationHexagon.isBlockade();
            handleMovementWithSacrifice(player, destinationHexagon);
            if (!isBlockade) {
                Platform.runLater(() -> {
                    MapMovementHandler.updatePlayerPosition(player, destinationHexagon, map);
                });
            }
            continueMoving = false;

        } else if (!compareColorAndHexagonType(player.getPlayedCardColor(), destinationHexagon.getType())) {

            ioHandler.printMessage("The color of the hexagon does not match the color of the card");

        } else if (notEnoughPower(player, destinationHexagon)){

            ioHandler.printMessage("You don't have enough power to get on this hexagon");

        } else {

            player.setPower(player.getPower() - destinationHexagon.getValue());
            boolean isBlockade = destinationHexagon.isBlockade();
            movePlayer(player, destinationHexagon);
            if (!isBlockade) {
                Platform.runLater(() -> {
                    MapMovementHandler.updatePlayerPosition(player, destinationHexagon, map);
                });
            }
            if(destinationHexagon.isFinal()){
                continueMoving = false;
            }
        }

        return continueMoving;
    }

    private boolean prohibitionByOccupancy(Player player, Hexagon destinationHexagon){
        return mapHandler.isHexagonOccupied(destinationHexagon) &&
                !player.isOccupiedMoveToken();
    }
    private boolean isSacrificeCardHexagon(Hexagon destinationHexagon){
        return destinationHexagon.getType() == HexagonType.red ||
                destinationHexagon.getType() == HexagonType.gray;
    }
    private boolean notEnoughPower(Player player, Hexagon destinationHexagon){
        return destinationHexagon.getValue() > player.getPower();
    }

    private MovementDirection chooseMovementDirection(){
        String infoString = ioHandler.getInfoString(InfoString.ChooseMovementDirection);
        int directionNumber = ioHandler.inputIntegerInRange(infoString, -1, 6) - 1;

        if(directionNumber == -1){
            return null;
        }
        return MovementDirection.values()[directionNumber];
    }

    private void handleMovementWithSacrifice(Player player, Hexagon destinationHexagon){
        int sacrificeNumber = destinationHexagon.getValue();

        while (true){

            if(player.getHandSize() < sacrificeNumber){
                ioHandler.printMessage("You don't have enough cards");
                break;
            }

            ioHandler.printTable("Hand", player.handToString());
            int cardNumber = ioHandler.inputIntegerInRange("Choose a card to sacrifice:",
                    0, player.getHandSize()) - 1;

            if(destinationHexagon.getType() == HexagonType.gray){
                player.discardCard(cardNumber);
            }
            else{
                player.removeCardFromGame(cardNumber);
            }

            sacrificeNumber -= 1;

            if(sacrificeNumber == 0){
                System.err.println("================================");
                movePlayer(player, destinationHexagon);
                break;
            }
        }
    }

    public void nativeAction(Player player){
        Hexagon playerInitialHexagon = player.getHexagon();

        while(true){

            MovementDirection movementDirection = chooseMovementDirection();
            if(movementDirection == null){
                break;
            }

            Hexagon destinationHexagon = mapHandler.getHexagonInDirection(playerInitialHexagon,
                    movementDirection.ordinal());

            boolean continueMoving = tryNativeMove(player, destinationHexagon);
            if(!continueMoving){
                break;
            }

        }
    }

    private boolean tryNativeMove(Player player, Hexagon destinationHexagon){
        boolean continueMoving = true;
        if(destinationHexagon == null){
            ioHandler.printMessage("You can't go outside the map");
        } else if (prohibitionByOccupancy(player, destinationHexagon)) {
            ioHandler.printMessage("The hexagon is occupied");
        } else if (destinationHexagon.getType() == HexagonType.black) {
            ioHandler.printMessage("You can't move here");
        } else {
            movePlayer(player, destinationHexagon);
            continueMoving = false;
        }
        return continueMoving;
    }

    private void movePlayer(Player player, Hexagon destinationHexagon){
        Hexagon playerInitialHexagon = player.getHexagon();

        if(checkIfHexagonIsBlockade(destinationHexagon)){
            destroyBlockade(player, destinationHexagon);
        }
        else{
            tryGetTokens(player, playerInitialHexagon, destinationHexagon);

            mapHandler.freeHexagon(playerInitialHexagon);
            player.setHexagon(destinationHexagon);
            mapHandler.occupyHexagon(destinationHexagon);

            if(destinationHexagon.isFinal()){
                player.setReachedTheEnd(true);
                mapHandler.freeHexagon(player.getHexagon());
            }
        }
    }


    public boolean compareColorAndHexagonType(Color color, HexagonType hexagonType){
        String colorString = colorToString(color);
        String hexagonTypeString = hexagonTypeToString(hexagonType);
        return colorString.equals(hexagonTypeString);
    }

    private String colorToString(Color color){
        return switch (color) {
            case WHITE -> "white";
            case BLUE -> "blue";
            case GREEN -> "green";
            case PURPLE -> "purple";
            case YELLOW -> "yellow";
        };
    }
    private String hexagonTypeToString(HexagonType hexagonType){
        return switch (hexagonType) {
            case red -> "red";
            case blue -> "blue";
            case gray -> "gray";
            case black -> "black";
            case green -> "green";
            case gold -> "yellow";
            case empty -> "empty";
        };
    }

    public boolean checkIfHexagonIsBlockade(Hexagon hexagon){
        return mapHandler.isHexagonBlockade(hexagon);
    }

    public void destroyBlockade(Player player, Hexagon blockade){
        Blockade destroyedBlockade = mapHandler.destroyBlockade(blockade);
        player.putNewBlockade(destroyedBlockade);
    }

    private void tryGetTokens(Player player, Hexagon initialHexagon, Hexagon newHexagon){
        if(!caveRules){
            return;
        }
        List<Hexagon> initialNeighbourCaves = mapHandler.getNeighbourCaves(initialHexagon);
        List<Hexagon> newNeighbourCaves = mapHandler.getNeighbourCaves(newHexagon);

        for (Hexagon newNeighbourCave : newNeighbourCaves) {
            if (!initialNeighbourCaves.contains(newNeighbourCave)) {
                Token token = mapHandler.takeTokenFromCave(newNeighbourCave);
                if (token != null) {
                    player.putNewToken(token);
                    ioHandler.printMessage("You received a token");
                    break;
                }
            }
        }

    }

    private void printDestinationHexagonInfo(Hexagon destinationHexagon){
        if(destinationHexagon != null){
            ioHandler.printMessage(destinationHexagon.getType().toString());
            ioHandler.printMessage("coord: " + destinationHexagon.getQCoord() + ";" + destinationHexagon.getRCoord());
            ioHandler.printMessage("Is blockade: " + destinationHexagon.isBlockade());
        }
        else{
            ioHandler.printMessage("NULL");
        }
    }

}
