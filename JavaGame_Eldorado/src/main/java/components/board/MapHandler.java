package components.board;

import components.board.serialization.DeserializeJSON;
import components.board.serialization.JarMain;
import components.board.serialization.JarRunner;
import components.tokens.Token;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class MapHandler {
    @Getter
    private Map map;
    private final String filename = "board_config.json";
    private final String jarName = "eldorado-team5.jar";
    public MapHandler(Map map){
        this.map = map;
    }

    /**
     * Generate map from path
     * @param path - directory in which eldorado-team5.jar is located
     */
    public Map generateMapFromJar(String path){
        try {
            JarRunner.runJar(path+jarName);
            DeserializeJSON deserializeJSON = new DeserializeJSON();
            map = deserializeJSON.deserializeJsonIntoMap(path + filename);
        }
        catch (InterruptedException | IOException ex){
            log.warn("Something went wrong: " + ex.getMessage());
        }
        return map;
    }

    /**
     * Generate map from our configuration
     * @param param - map type: A or B
     */
    public void generateMapFromPresets(String param){
        map = JarMain.makeMap(param, map);
    }

    public void occupyHexagon(Hexagon hexagon){
        hexagon.Occupy();
    }

    public void freeHexagon(Hexagon hexagon){
        hexagon.Free();
    }

    public boolean isHexagonOccupied(Hexagon hexagon){
        return hexagon.getNumberOfPlayers() > 0;
    }

    public boolean isHexagonBlockade(Hexagon hexagon){
        return hexagon.isBlockade();
    }

    public Token takeTokenFromCave(Hexagon hexagon){
        if(hexagon.tokens.isEmpty()){
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(hexagon.tokens.size());
        return hexagon.tokens.remove(index);
    }

    public List<Hexagon> getNeighbourCaves(Hexagon hexagon){
        List<Hexagon> caves = new ArrayList<>();
        for(Hexagon neighbours: hexagon.getNeighbors()){
            if (neighbours != null && neighbours.isActive() && neighbours.getType() == HexagonType.black){
                caves.add(neighbours);
            }
        }
        return caves;
    }

    public Blockade destroyBlockade(Hexagon hexagon){
        if (hexagon.isBlockade()) {
            hexagon.unsetBlockade();

            ArrayList<Blockade> blockades = map.getBlockades();
            Blockade destroyedBlockade = null;
            for(Blockade blockade: blockades){
                if(hexagon == blockade.blockadeHex){
                    destroyedBlockade = blockade;
                    break;
                }
            }
            return destroyedBlockade;
        }
        else {
            throw new IllegalArgumentException("Hexagon is not a blockade!");
        }
    }

    /**
     * @param hexagon - Current hexagon
     * @param direction - 0-topright 1-right 2-botright 3-botleft 4-left 5-topleft
     * @return - needed neighbour, can be null if there is no neighbor
     */
    public Hexagon getHexagonInDirection(Hexagon hexagon, int direction){
//        Platform.runLater(() -> {
//            for (int i = 0; i < map.getHexagonalGrids().get(0).getRows().get(6).get(3).getNeighbors().length; i++) {
//                System.out.println(map.getHexagonalGrids().get(0).getRows().get(6).get(3).getNeighbors()[i]);
//            }
//        });
        Hexagon destinationHex = hexagon.getNeighbors()[direction];
        if(destinationHex == null || !destinationHex.isActive()){
            return null;
        }
        Blockade blockadeOnTheWay = makeStep(hexagon, destinationHex);
        if(blockadeOnTheWay == null || !blockadeOnTheWay.blockadeHex.isBlockade()){
            return destinationHex;
        }

        return blockadeOnTheWay.blockadeHex;
    }

    public ArrayList<Hexagon> getStartingHexagons(){
        HexagonalGrid hex = (HexagonalGrid) map.getElementByName("A");
        int rotation = hex.getRotation();
        return hex.getHexagonSide(((rotation/60)+4)%6);
    }

    /**
     * Defines if a player can make a step to a new hexagon.
     * @param playerHex - initial Hexagon
     * @param newDirection - direction for a new step
     * @return - null if the newDirection is inside the same Tile as player,
     * returns blockade if the newDirection is inside new Tile
     * returns null if there is no blockade between two tiles (for example between TerrainStrip and HexagonalGrid)
     */
    public Blockade makeStep(Hexagon playerHex, Hexagon newDirection){
        if(newDirection == null || !newDirection.isActive()){
            return null;
        }
        Tile playerTile = map.findHexInElements(playerHex);
        Tile mapTile = map.findHexInElements(newDirection);
        if(playerTile == mapTile){
            return null;
        }
        else{
            return map.findBlockadeByTilesName(playerTile, mapTile);
        }

    }

}
