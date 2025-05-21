package components.gameManager;

import components.gameManager.ActionHandlers.TurnHandler.TurnHandlerOneFigureGame;
import components.gameManager.ActionHandlers.TurnHandler.TurnHandlerStrategy;
import components.gameManager.ActionHandlers.TurnHandler.TurnHandlerTwoFiguresGame;
import components.gameManager.DecideWinner.DecideWinnerOneFigureGame;
import components.gameManager.DecideWinner.DecideWinnerStrategy;
import components.gameManager.DecideWinner.DecideWinnerTwoFiguresGame;
import components.gameManager.IO.IOHandler;
import components.gameManager.IO.InfoString;
import components.player.Player;
import javafx.application.Platform;
import ui.MapInitializer;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static volatile GameManager instance;
    public List<Player> players;
    public IOHandler ioHandler;
    public DecideWinnerStrategy decideWinnerStrategy;
    public TurnHandlerStrategy turnHandlerStrategy;

    private boolean lastRound;
    public boolean twoPlayersGame;

    public GameManager(IOHandler ioHandler){
        this.ioHandler = ioHandler;
        players = new ArrayList<Player>();
        decideWinnerStrategy = new DecideWinnerOneFigureGame();
        turnHandlerStrategy = new TurnHandlerOneFigureGame(players);
    }

    public static GameManager getInstance(IOHandler ioHandler) {
        if (instance == null) {
            synchronized (GameManager.class) {
                if (instance == null) {
                    instance = new GameManager(ioHandler);
                }
            }
        }
        return instance;
    }

    public void startGame(){

        enterMap();
        enterPlayersData();
        enterCavesRule();

        lastRound = false;
        while(!lastRound){
            for (Player player : players) {
                handleTurn(player);
            }
        }

        Player winner = decideWinner();
        ioHandler.printMessage("The winner is " + winner.getColor() + " player!");
    }


    public void enterPlayersData(){
        String[] colors = {"Red", "White", "Blue", "Yellow"};
        players = new ArrayList<Player>();

        int playersNumber = ioHandler.inputIntegerInRange("Enter number of players",
                0, 4);

        twoPlayersGame = (playersNumber == 2);
        if(twoPlayersGame){
            playersNumber = 4;
        }

        for(int i = 0; i < playersNumber; i++){
            Player player = new Player(colors[i]);
            players.add(player);
        }

        if(twoPlayersGame){
            players.get(0).equalizeResources(players.get(2));
            players.get(1).equalizeResources(players.get(3));
        }

        setStrategies();
        turnHandlerStrategy.placePlayersAtStart();
//        players.get(0).setHexagon(turnHandlerStrategy.getMovementHandler().map.getMap().get(new Coordinate(16, -1)));
    }

    public void enterCavesRule(){
        int decision = ioHandler.inputIntegerInRange(ioHandler.getInfoString(InfoString.CaveRulesDecision),
                0, 2);
        turnHandlerStrategy.setCaveRules(decision == 1);
    }

    public void enterMap(){
        int decision = ioHandler.inputIntegerInRange(ioHandler.getInfoString(InfoString.MapSelection),
                0, 3) - 1;
        String param = new String[]{"A", "B", "JAR"}[decision];
//        turnHandlerStrategy.movementHandler.setChosenMap(param);
        Platform.runLater(() -> {
            switch (param) {
                case "A", "JAR" -> MapInitializer.initMapA();
                case "B" -> MapInitializer.initMapB();
            }
        });
        turnHandlerStrategy.getMovementHandler().initializeMap(param);
    }

    private void setStrategies(){
        if(twoPlayersGame){
            decideWinnerStrategy = new DecideWinnerTwoFiguresGame();
            turnHandlerStrategy = new TurnHandlerTwoFiguresGame(players);
        }
        else{
            decideWinnerStrategy = new DecideWinnerOneFigureGame();
            turnHandlerStrategy.setPlayers(players);
        }
    }

    public Player decideWinner(){
        return decideWinnerStrategy.decideWinner(players);
    }

    public void handleTurn(Player player){
        lastRound = turnHandlerStrategy.handleTurn(player);
    }

    public void enterTestMapA(){
        MapInitializer.initMapA();
        turnHandlerStrategy.getMovementHandler().initializeMap("A");
    }


}
