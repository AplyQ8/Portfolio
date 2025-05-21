package components.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import components.tokens.ExpeditionToken;
import components.tokens.Token;
import components.tokens.TokenAction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Getter
public class Map {
    @JsonIgnore
    HashMap<Coordinate, Hexagon> map = new HashMap<>();
    ArrayList<Tile> elements = new ArrayList<>();
    public void initializeMap(){
        for (int r = -50; r < 51; r++) {
            for (int q = -50; q < 51; q++) {
                Hexagon hex = new Hexagon(HexagonType.empty, -1, false, q, r);
                Coordinate coordinate = new Coordinate(q, r);
                map.put(coordinate, hex);
            }
        }
    }

    private void setActive(Hexagon hex){
        int qCoord = hex.getQCoord();
        int rCoord = hex.getRCoord();
        Coordinate coordinate = new Coordinate(qCoord, rCoord);
        if (map.get(coordinate).isActive()){
            throw new IllegalArgumentException("Map elements intercept each other!");
        }
        if (map.containsKey(coordinate)){
            map.put(coordinate, hex);
        }
        map.get(coordinate).setIsActive();

        if(hex.getType() == HexagonType.black){
            setTokensForHex(hex);
        }
    }

    private void setTokensForHex(Hexagon hex){
        for(int i = 0; i < 4; i++){
            putRandomTokenInHex(hex);
        }
    }

    private void putRandomTokenInHex(Hexagon hex){
        TokenAction action = Token.getRandomAction();
        Token token;
        if(action == TokenAction.Expedition){
            token = ExpeditionToken.getRandomExpToken();
        }
        else{
            token = new Token(action);
        }

        hex.tokens.add(token);
    }

    public void addElement(HexagonalGrid grid){
        elements.add(grid);
        for (int i = 0; i < grid.getRows().size(); i++) {
            for (int j = 0; j < grid.getRows().get(i).size(); j++) {
                Hexagon hex = grid.getRows().get(i).get(j);
                setActive(hex);
            }
        }
    }

    public void addElement(TerrainStrip strip){
        elements.add(strip);
        for (int i = 0; i < strip.getRows().size(); i++) {
            for (int j = 0; j < strip.getRows().get(i).size(); j++) {
                Hexagon hex = strip.getRows().get(i).get(j);
                setActive(hex);
            }
        }
    }

    public void addElement(Blockade blockade){
        elements.add(blockade);
    }

    public void addElement(EndTile endTile){
        elements.add(endTile);
        for (int i = 0; i < endTile.getEnd().size(); i++) {
            Hexagon hex = endTile.getEnd().get(i);
            setActive(hex);
        }
    }

    public void launchMap(){
        for (Coordinate coord : map.keySet()) {
            Hexagon hex = map.get(coord);
            for (int i = 0; i < hex.getNeighbors().length; i++) {
                Coordinate neighbourCoord = getNeighbourCoord(hex, i);
                if (map.containsKey(neighbourCoord)) {
                    if (map.get(neighbourCoord).isActive() && hex.isActive() && hex.getNeighbors()[i] != map.get(neighbourCoord)) {
                        hex.setNeighbor(i, map.get(neighbourCoord));
                    }
                }
            }
        }
    }

    private static Coordinate getNeighbourCoord(Hexagon hex, int i) {
        int qCoord = hex.getQCoord();
        int rCoord = hex.getRCoord();
        if (i == 0){
            qCoord += 1;
            rCoord -= 1;
        }
        else if (i == 1){
            qCoord += 1;
        }
        else if (i == 2){
            rCoord += 1;
        }
        else if (i == 3){
            qCoord -= 1;
            rCoord += 1;
        } else if (i == 4) {
            qCoord -= 1;
        }
        else if (i == 5){
            rCoord -= 1;
        }
        return new Coordinate(qCoord, rCoord);
    }

    public ArrayList<HexagonalGrid> getHexagonalGrids(){
        ArrayList<HexagonalGrid> arr = new ArrayList<>();
        for(Tile tile : elements){
            if (tile instanceof HexagonalGrid){
                arr.add((HexagonalGrid) tile);
            }
        }
        return arr;
    }

    public ArrayList<TerrainStrip> getTerrainStrips(){
        ArrayList<TerrainStrip> arr = new ArrayList<>();
        for(Tile tile : elements){
            if (tile instanceof TerrainStrip){
                arr.add((TerrainStrip) tile);
            }
        }
        return arr;
    }

    public EndTile getEndTiles(){
        for(Tile tile : elements){
            if (tile instanceof EndTile){
                return (EndTile) tile;
            }
        }
        return null;
    }

    public ArrayList<Blockade> getBlockades(){
        ArrayList<Blockade> arr = new ArrayList<>();
        for(Tile tile : elements){
            if (tile instanceof Blockade){
                arr.add((Blockade) tile);
            }
        }
        return arr;
    }

    public Tile getElementByName(String name){
        for (Tile tile: elements){
            if (tile.getName() != null && tile.getName().equals(name))
                return tile;
        }
        return null;
    }

    public Tile findHexInElements(Hexagon hex){
            for (Tile tile: elements){
                if (tile instanceof HexagonalGrid){
                    for (var el : ((HexagonalGrid) tile).getRows()){
                        if (el.contains(hex))
                            return tile;
                    }
                }
                else if (tile instanceof TerrainStrip){
                    for (var el: ((TerrainStrip) tile).getRows()){
                        if(el.contains(hex))
                            return tile;
                    }
                }
                else if (tile instanceof EndTile){
                    for (var el: ((EndTile) tile).getEnd()){
                        if(el == hex)
                            return tile;
                    }
                }
            }
            return null;
    }

    public Blockade findBlockadeByTilesName(Tile tileA, Tile tileB){
        ArrayList<Blockade> blockades = getBlockades();
        for (var el : blockades){
            if(Objects.equals(el.neighbourA.getName(), tileA.getName()) &&
                    Objects.equals(el.neighbourB.getName(), tileB.getName())){
                return el;
            }
        }
        return null;
    }

    /**
     * To make a step on the map:
     * 1. player has hexagon for his current location and tile in which this hexagon is
     * 2. player gets available spaces to go to by choosing hex.getNeighbours() != null
     * 3. player checks that chosen neighbour hex is inside current Tile/TerrainStrip
     * 3a. If so, player makes turn
     * 3b. If hex is a part of a new structure (new tile/terrainstrip) such structure should be found in elements array
     * 3c. By obtaining names of two tiles Tile.getName() player is able to locate a blockade if it exists in elements array
     * 3d. If it is deactivated just go through
     * 3e. If it is active cards should be played and player can not cross there yet.
     */

}
