package components.board.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.board.*;
import components.board.presets.MapA;
import components.board.presets.MapB;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SerializeTest {
    @Test
    void serializeEndTile() throws JsonProcessingException {
        EndTile tile = new EndTile(true, 0,-1,0);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writeValueAsString(tile);
        assertEquals("{\"rotation\":0,\"type\":\"green\",\"coordinates\":[-1,0]}",
                jsonResult);
    }

    @Test
    void serializeBlockade() throws JsonProcessingException {
        List<List<Hexagon>> grid = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<Hexagon> row = new ArrayList<>();
            for (int j = 0; j < 7 - Math.abs(3 - i); j++) {
                row.add(new Hexagon(HexagonType.green, i));
            }
            grid.add(row);
        }
        HexagonalGrid hexagonalGridA = new HexagonalGrid(grid,0 ,0);
        hexagonalGridA.setName("A");
        HexagonalGrid hexagonalGridB = new HexagonalGrid(grid,0 ,0);
        hexagonalGridB.setName("B");
        Blockade blockade = new Blockade(1, hexagonalGridA, hexagonalGridB, HexagonType.green, 1);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writeValueAsString(blockade);
        assertEquals("{\"power\":1,\"number\":1,\"type\":\"green\",\"between\":[\"A\",\"B\"]}", jsonResult);
    }

    @Test
    void serializeHexagonalGrid() throws JsonProcessingException {
        List<List<Hexagon>> grid = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<Hexagon> row = new ArrayList<>();
            for (int j = 0; j < 7 - Math.abs(3 - i); j++) {
                row.add(new Hexagon(HexagonType.green, i));
            }
            grid.add(row);
        }
        HexagonalGrid hexagonalGrid = new HexagonalGrid(grid,-1, 1, 180);
        hexagonalGrid.setName("A");
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writeValueAsString(hexagonalGrid);
        assertEquals("{\"rotation\":180,\"coordinates\":[-1,1],\"tileType\":\"A\"}",
                jsonResult);
    }

    TerrainStrip terrainStrip;
    @Test
    void serializeTerrainStrip() throws JsonProcessingException {
        List<List<Hexagon>> grid = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Hexagon> row = new ArrayList<>();
            for (int j = 0; j < 6 - Math.abs(1 - i); j++) {
                row.add(new Hexagon(HexagonType.green, i));
            }
            grid.add(row);
        }
        terrainStrip = new TerrainStrip(grid, 0, 0, 0, 1, 0);
        terrainStrip.setName("New");

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writeValueAsString(terrainStrip);

        assertEquals("{\"tileType\":\"New\",\"rotation\":0,\"coordinates\":[[0,0],[1,0]]}",
                jsonResult);
    }

    @Test
    void serializeMap() throws JsonProcessingException {
        MapA mapA = new MapA();
        Map map = mapA.loadMap();
        TileWrapper tileWrapper = new TileWrapper(map.getHexagonalGrids(), map.getTerrainStrips(),
                map.getBlockades(), map.getEndTiles());
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writeValueAsString(tileWrapper);
        System.out.println(jsonResult);
        assertEquals("{\"tiles\":[{\"rotation\":0,\"coordinates\":[0,0],\"tileType\":\"A\"}," +
                        "{\"rotation\":60,\"coordinates\":[7,-3],\"tileType\":\"I\"}," +
                        "{\"rotation\":60,\"coordinates\":[14,-7],\"tileType\":\"C\"}," +
                        "{\"rotation\":120,\"coordinates\":[21,-10],\"tileType\":\"M\"}," +
                        "{\"rotation\":60,\"coordinates\":[9,2],\"tileType\":\"K\"}," +
                        "{\"rotation\":120,\"coordinates\":[19,-3],\"tileType\":\"G\"}," +
                        "{\"rotation\":180,\"coordinates\":[26,-5],\"tileType\":\"E\"}]," +
                        "\"strips\":[{\"tileType\":\"Q\",\"rotation\":60,\"coordinates\":[[14,-1],[14,0]]}," +
                        "{\"tileType\":\"O\",\"rotation\":0,\"coordinates\":[[22,0],[23,0]]}]," +
                        "\"blockades\":[{\"power\":1,\"number\":3,\"type\":\"gray\",\"between\":[\"A\",\"I\"]}," +
                        "{\"power\":1,\"number\":2,\"type\":\"gold\",\"between\":[\"I\",\"C\"]}," +
                        "{\"power\":1,\"number\":1,\"type\":\"green\",\"between\":[\"C\",\"M\"]}," +
                        "{\"power\":1,\"number\":4,\"type\":\"blue\",\"between\":[\"I\",\"K\"]}," +
                        "{\"power\":2,\"number\":6,\"type\":\"gray\",\"between\":[\"M\",\"G\"]}," +
                        "{\"power\":2,\"number\":5,\"type\":\"green\",\"between\":[\"G\",\"E\"]}]," +
                        "\"endTile\":{\"rotation\":120,\"type\":\"blue\",\"coordinates\":[24,2]}}",
                jsonResult);
    }

    @Test
    void serialize(){
        SerializeJSON serializeJSON = new SerializeJSON();
        MapB mapB = new MapB();
        Map map = mapB.loadMap();
        assertDoesNotThrow(() -> serializeJSON.serializeIntoJsonFile(map));
    }

}