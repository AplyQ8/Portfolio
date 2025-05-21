package components.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HexagonalGridTest {

    HexagonalGrid hexagonalGrid;

    @BeforeEach
    void setUp(){
        List<List<Hexagon>> grid = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<Hexagon> row = new ArrayList<>();
            for (int j = 0; j < 7 - Math.abs(3 - i); j++) {
                row.add(new Hexagon(HexagonType.green, i));
            }
            grid.add(row);
        }
        hexagonalGrid = new HexagonalGrid(grid,-1, 1);
    }

    @Test
    void rotationTest(){
        List<List<Hexagon>> grid = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<Hexagon> row = new ArrayList<>();
            for (int j = 0; j < 7 - Math.abs(3 - i); j++) {
                row.add(new Hexagon(HexagonType.green, i));
            }
            grid.add(row);
        }
        hexagonalGrid = new HexagonalGrid(grid,-1, 1, 60);

        assertEquals(3, hexagonalGrid.getRows().get(0).get(0).getValue());
        assertEquals(2, hexagonalGrid.getRows().get(0).get(1).getValue());
        assertEquals(1, hexagonalGrid.getRows().get(0).get(2).getValue());
        assertEquals(0, hexagonalGrid.getRows().get(0).get(3).getValue());
        assertEquals(2, hexagonalGrid.getRows().get(1).get(4).getQCoord());
        assertEquals(-1, hexagonalGrid.getRows().get(1).get(4).getRCoord());

    }

    @Test
    void canGetHexTopSide(){
        List<Hexagon> topSide = hexagonalGrid.getRows().get(0);
        assertEquals(topSide, hexagonalGrid.getHexagonSide(1));
    }

    @Test
    void canGetHexTopRightSide(){
        List<Hexagon> topRightSide = new ArrayList<>();
        topRightSide.add(hexagonalGrid.getRows().get(0).get(hexagonalGrid.getRows().get(0).size() - 1));
        topRightSide.add(hexagonalGrid.getRows().get(1).get(hexagonalGrid.getRows().get(1).size() - 1));
        topRightSide.add(hexagonalGrid.getRows().get(2).get(hexagonalGrid.getRows().get(2).size() - 1));
        topRightSide.add(hexagonalGrid.getRows().get(3).get(hexagonalGrid.getRows().get(3).size() - 1));
        assertEquals(topRightSide, hexagonalGrid.getHexagonSide(2));
    }

    @Test
    void canGetHexBottomRightSide(){
        List<Hexagon> bottomRightSide = new ArrayList<>();
        bottomRightSide.add(hexagonalGrid.getRows().get(3).get(hexagonalGrid.getRows().get(3).size() - 1));
        bottomRightSide.add(hexagonalGrid.getRows().get(4).get(hexagonalGrid.getRows().get(4).size() - 1));
        bottomRightSide.add(hexagonalGrid.getRows().get(5).get(hexagonalGrid.getRows().get(5).size() - 1));
        bottomRightSide.add(hexagonalGrid.getRows().get(6).get(hexagonalGrid.getRows().get(6).size() - 1));
        assertEquals(bottomRightSide, hexagonalGrid.getHexagonSide(3));
    }

    @Test
    void canGetHexBottomSide(){
        List<Hexagon> bottomSide = hexagonalGrid.getRows().get(6);
        assertEquals(bottomSide, hexagonalGrid.getHexagonSide(4));
    }

    @Test
    void canGetHexBottomLeftSide(){
        List<Hexagon> bottomLeftSide = new ArrayList<>();
        bottomLeftSide.add(hexagonalGrid.getRows().get(3).get(0));
        bottomLeftSide.add(hexagonalGrid.getRows().get(4).get(0));
        bottomLeftSide.add(hexagonalGrid.getRows().get(5).get(0));
        bottomLeftSide.add(hexagonalGrid.getRows().get(6).get(0));
        assertEquals(bottomLeftSide, hexagonalGrid.getHexagonSide(5));
    }

    @Test
    void canGetHexTopLeftSide(){
        List<Hexagon> topLeftSide = new ArrayList<>();
        topLeftSide.add(hexagonalGrid.getRows().get(0).get(0));
        topLeftSide.add(hexagonalGrid.getRows().get(1).get(0));
        topLeftSide.add(hexagonalGrid.getRows().get(2).get(0));
        topLeftSide.add(hexagonalGrid.getRows().get(3).get(0));
        assertEquals(topLeftSide, hexagonalGrid.getHexagonSide(6));
    }

    @Test
    void cantGetOtherSides(){
        assertThrows(IllegalArgumentException.class, () -> hexagonalGrid.getHexagonSide(10));
    }

    @Test
    void hexNeighboursAreSet(){
        Hexagon hex = hexagonalGrid.getRows().get(3).get(3);
        assertEquals(hexagonalGrid.getRows().get(3).get(2), hex.getNeighbors()[4]);
        assertEquals(hexagonalGrid.getRows().get(3).get(4), hex.getNeighbors()[1]);
        assertEquals(hexagonalGrid.getRows().get(2).get(3), hex.getNeighbors()[0]);
        assertEquals(hexagonalGrid.getRows().get(4).get(3), hex.getNeighbors()[2]);
        assertEquals(hexagonalGrid.getRows().get(4).get(2), hex.getNeighbors()[3]);
        assertEquals(hexagonalGrid.getRows().get(2).get(2), hex.getNeighbors()[5]);
    }

    @Test
    void getCenterCoordinatesTest(){
        assertEquals(-1, hexagonalGrid.getCoordinates()[0]);
        assertEquals(1, hexagonalGrid.getCoordinates()[1]);
    }

    @Test
    void getOtherCoordinates(){
        assertEquals(-1, hexagonalGrid.getRows().get(0).get(0).getQCoord());
        assertEquals(-2, hexagonalGrid.getRows().get(0).get(0).getRCoord());
    }



}