package components.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TerrainStripTest {

    HexagonalGrid hexagonalGrid;
    TerrainStrip terrainStrip;

    @BeforeEach
    void setUp(){
        List<List<Hexagon>> grid = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Hexagon> row = new ArrayList<>();
            for (int j = 0; j < 6 - Math.abs(1 - i); j++) {
                row.add(new Hexagon(HexagonType.green, i));
            }
            grid.add(row);
        }
        terrainStrip = new TerrainStrip(grid, 0, 0, 0, 1, 0);
    }

    @Test
    void testStructure(){
        assertEquals(5, terrainStrip.getRows().get(0).size());
        assertEquals(6, terrainStrip.getRows().get(1).size());
        assertEquals(5, terrainStrip.getRows().get(2).size());
    }

    @Test
    void getCoordinates(){
        ArrayList<int[]> arr = terrainStrip.getCoordinates();
        assertEquals(arr.get(0)[0], 0);
        assertEquals(arr.get(0)[1], 0);
        assertEquals(arr.get(1)[0], 1);
        assertEquals(arr.get(1)[1], 0);

    }
}