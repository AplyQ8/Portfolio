package components.board;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlockadeTest {

    @Test
    public void makeBlockade(){
        List<List<Hexagon>> grid = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<Hexagon> row = new ArrayList<>();
            for (int j = 0; j < 7 - Math.abs(3 - i); j++) {
                row.add(new Hexagon(HexagonType.green, i));
            }
            grid.add(row);
        }
        HexagonalGrid hexagonalGridA = new HexagonalGrid(grid,0 ,0);
        HexagonalGrid hexagonalGridB = new HexagonalGrid(grid,0 ,0);
        Blockade blockade = new Blockade(1, hexagonalGridA, hexagonalGridB, HexagonType.green, 1);
        assertTrue(blockade.isActive);
        assertEquals(blockade.power, 1);
        assertEquals(blockade.neighbourA, hexagonalGridA);
        assertEquals(blockade.neighbourB, hexagonalGridB);
    }

    @Test
    public void deactivateBlockadeTest(){
        List<List<Hexagon>> grid = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<Hexagon> row = new ArrayList<>();
            for (int j = 0; j < 7 - Math.abs(3 - i); j++) {
                row.add(new Hexagon(HexagonType.green, i));
            }
            grid.add(row);
        }
        HexagonalGrid hexagonalGridA = new HexagonalGrid(grid,0 ,0);
        HexagonalGrid hexagonalGridB = new HexagonalGrid(grid,0 ,0);
        Blockade blockade = new Blockade(1, hexagonalGridA, hexagonalGridB, HexagonType.gold, 1);
        blockade.deactivateBlockade();
        assertFalse(blockade.isActive);
    }

}