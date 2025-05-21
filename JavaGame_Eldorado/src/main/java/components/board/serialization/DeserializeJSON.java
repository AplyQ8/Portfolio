package components.board.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import components.board.*;

import java.io.File;
import java.util.ArrayList;

public class DeserializeJSON {
    public Map deserializeJsonIntoMap(String path){
        Map map = new Map();
        map.initializeMap();
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Path to your JSON file
            File file = new File(path);

            SimpleModule module = new SimpleModule();
            module.addDeserializer(HexagonalGrid.class, new HexagonalGridDeserializer());
            module.addDeserializer(TerrainStrip.class, new TerrainStripDeserializer());
            module.addDeserializer(Blockade.class, new BlockadeDeserializer());
            module.addDeserializer(EndTile.class, new EndTileDeserializer());
            objectMapper.registerModule(module);

            // Deserialize JSON file to TileWrapper object
            TileWrapper tileWrapper = objectMapper.readValue(file, TileWrapper.class);

            map.addElement(tileWrapper.getEndTiles());
            for(HexagonalGrid hex : tileWrapper.getTiles()){
                map.addElement(hex);
            }
            for(TerrainStrip strip : tileWrapper.getStrips()){
                map.addElement(strip);
            }
            ArrayList<Blockade> blockades = tileWrapper.getBlockades();
            for(Blockade bl : blockades){
                Tile tileA = map.getElementByName(bl.getNeighbourA().getName());
                bl.setNeighbourA(tileA);
                Tile tileB = map.getElementByName(bl.getNeighbourB().getName());
                bl.setNeighbourB(tileB);
            }
            for (Blockade bl : blockades){
                map.addElement(bl);
            }
            map.launchMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private void addElementsToMap(Map map, ArrayList<Tile> elements){

    }
}
