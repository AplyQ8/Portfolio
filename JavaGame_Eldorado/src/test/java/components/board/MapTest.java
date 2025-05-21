package components.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {
    HexagonalGrid hexagonalGrid;
    Map map;
    TerrainStrip terrainStrip;
    Blockade blockade;
    EndTile endTile;

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
        hexagonalGrid = new HexagonalGrid(grid,0, 0);

        map = new Map();
        map.initializeMap();

        grid = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Hexagon> row = new ArrayList<>();
            for (int j = 0; j < 6 - Math.abs(1 - i); j++) {
                row.add(new Hexagon(HexagonType.blue, i));
            }
            grid.add(row);
        }
        terrainStrip = new TerrainStrip(grid, 180, 7,0,6,0);

        blockade = new Blockade(1, hexagonalGrid, terrainStrip, HexagonType.green,1);

        endTile = new EndTile(true, 60, 10, 0);
    }

    @Test
    void initializeMap() {
        Coordinate coordinate = new Coordinate(-50, -50);
        Coordinate coordinate1 = new Coordinate(50, 50);
        assertEquals(map.getMap().get(coordinate).getType(), HexagonType.empty);
        assertEquals(map.getMap().get(coordinate).getQCoord(), -50);
        assertEquals(map.getMap().get(coordinate).getRCoord(), -50);
        assertEquals(map.getMap().get(coordinate1).getType(), HexagonType.empty);
        assertEquals(map.getMap().get(coordinate1).getQCoord(), 50);
        assertEquals(map.getMap().get(coordinate1).getRCoord(), 50);
    }

    @Test
    void addElementHexagonalGrid() {
        map.addElement(hexagonalGrid);
        for (int i = 0; i < hexagonalGrid.getRows().size(); i++) {
            for (int j = 0; j < hexagonalGrid.getRows().get(i).size(); j++) {
                Coordinate coor = new Coordinate(hexagonalGrid.getRows().get(i).get(j).getQCoord(),
                        hexagonalGrid.getRows().get(i).get(j).getRCoord());
                assertTrue(map.getMap().get(coor).isActive());
            }
        }
    }

    @Test
    void addElementTerrainStrip() {
        map.addElement(terrainStrip);
        for (int i = 0; i < terrainStrip.getRows().size(); i++) {
            for (int j = 0; j < terrainStrip.getRows().get(i).size(); j++) {
                Coordinate coor = new Coordinate(terrainStrip.getRows().get(i).get(j).getQCoord(),
                        terrainStrip.getRows().get(i).get(j).getRCoord());
                assertTrue(map.getMap().get(coor).isActive());
            }
        }
    }

    @Test
    void addElementBlockade() {
        map.addElement(blockade);
        assertTrue(map.getElements().contains(blockade));
    }

    @Test
    void addElementEndTile() {
        map.addElement(endTile);
        for (int i = 0; i < endTile.getEnd().size(); i++) {
            Coordinate coordinate = new Coordinate(endTile.getEnd().get(i).getQCoord(),
                    endTile.getEnd().get(i).getRCoord());
            assertTrue(map.getMap().get(coordinate).isActive());
        }
    }

    @Test
    void launchMap() {
        map.addElement(hexagonalGrid);
        map.addElement(terrainStrip);
        map.addElement(endTile);
        map.launchMap();
        Coordinate coordinate = new Coordinate(0,0);
        assertTrue(map.getMap().get(coordinate).isActive());
        assertEquals(map.getMap().get(coordinate).getType(), HexagonType.green);
        coordinate = new Coordinate(0,-3);
        assertTrue(map.getMap().get(coordinate).isActive());
        assertNull(map.getMap().get(coordinate).getNeighbors()[5]);
        assertNull(map.getMap().get(coordinate).getNeighbors()[4]);
        assertNull(map.getMap().get(coordinate).getNeighbors()[0]);
        assertNotNull(map.getMap().get(coordinate).getNeighbors()[1]);
        assertNotNull(map.getMap().get(coordinate).getNeighbors()[2]);
        assertNotNull(map.getMap().get(coordinate).getNeighbors()[3]);

        coordinate = new Coordinate(3,0);
        assertNotNull(map.getMap().get(coordinate).getNeighbors()[1]);
        assertEquals(map.getMap().get(coordinate).getNeighbors()[1].getType(), HexagonType.blue);

        coordinate = new Coordinate(10,-1);
        assertTrue(map.getMap().get(coordinate).isActive());
        assertNotNull(map.getMap().get(coordinate).getNeighbors()[2]);
        assertNotNull(map.getMap().get(coordinate).getNeighbors()[3]);
        assertNull(map.getMap().get(coordinate).getNeighbors()[0]);
        assertNull(map.getMap().get(coordinate).getNeighbors()[1]);
        assertNotNull(map.getMap().get(coordinate).getNeighbors()[4]);
        assertNull(map.getMap().get(coordinate).getNeighbors()[5]);

        coordinate = new Coordinate(20,20);
        assertFalse(map.getMap().get(coordinate).isActive());
    }
}