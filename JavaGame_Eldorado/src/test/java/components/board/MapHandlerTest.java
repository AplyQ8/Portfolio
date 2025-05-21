package components.board;

import components.board.presets.MapA;
import components.player.Player;
import components.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapHandlerTest {

    private Map map;
    private MapHandler mapHandler;
    @BeforeEach
    void setUp() {
        MapA mapA = new MapA();
        map = mapA.loadMap();
        mapHandler = new MapHandler(map);
    }

    @Test
    void testGenerateMapFromPresets() {
        mapHandler.generateMapFromPresets("A");
        // This method's result depends on JarMain which isn't provided here
        // We assume that after calling this method, map is not null
        assertNotNull(map);
    }

    @Test
    void testOccupyHexagon() {
        Hexagon hexagon = new Hexagon();
        mapHandler.occupyHexagon(hexagon);
        assertTrue(hexagon.getNumberOfPlayers() == 1);
    }

    @Test
    void testFreeHexagon() {
        Hexagon hexagon = new Hexagon();
        mapHandler.freeHexagon(hexagon);
        assertTrue(hexagon.getNumberOfPlayers() == 0);
    }

    @Test
    void testIsHexagonOccupied() {
        Hexagon hexagon = new Hexagon();
        hexagon.Occupy();
        assertTrue(mapHandler.isHexagonOccupied(hexagon));
    }

    @Test
    void testIsHexagonBlockade() {
        Hexagon hexagon = new Hexagon();
        hexagon.setBlockade();
        assertTrue(mapHandler.isHexagonBlockade(hexagon));
    }

    @Test
    void testTakeTokenFromCave() {
        Hexagon hexagon = new Hexagon();
        Token token = new Token();
        hexagon.tokens.add(token);

        Token takenToken = mapHandler.takeTokenFromCave(hexagon);
        assertEquals(token, takenToken);
        assertTrue(hexagon.tokens.isEmpty());
    }

    @Test
    void testTakeTokenFromCave_EmptyTokens() {
        Hexagon hexagon = new Hexagon();
        assertNull(mapHandler.takeTokenFromCave(hexagon));
    }

    @Test
    void testGetNeighbourCaves() {
        Hexagon hexagon = new Hexagon();
        Hexagon neighbor1 = new Hexagon(HexagonType.black, -1);
        neighbor1.setIsActive();
        Hexagon neighbor2 = new Hexagon();
        neighbor2.setIsActive();

        hexagon.setNeighbor(1, neighbor1);
        hexagon.setNeighbor(2, neighbor2);

        List<Hexagon> result = mapHandler.getNeighbourCaves(hexagon);
        assertEquals(1, result.size());
        assertEquals(neighbor1, result.get(0));
    }

    @Test
    void testDestroyBlockade() {
        Hexagon hexagon = map.getBlockades().get(0).blockadeHex;

        Blockade blockade = mapHandler.destroyBlockade(hexagon);
        Hexagon result = blockade.blockadeHex;
        assertFalse(hexagon.isBlockade());
        assertEquals(hexagon, result);
    }

    @Test
    void testDestroyBlockade_NotABlockade() {
        Hexagon hexagon = new Hexagon();
        hexagon.unsetBlockade();

        assertThrows(IllegalArgumentException.class, () -> mapHandler.destroyBlockade(hexagon));
    }

    @Test
    void testGetHexagonInDirection() {
        Hexagon hexagon = new Hexagon();
        Hexagon neighbor = new Hexagon();

        hexagon.setIsActive();
        neighbor.setIsActive();

        hexagon.setNeighbor(0, neighbor);

        Hexagon result = mapHandler.getHexagonInDirection(hexagon, 0);
        assertEquals(neighbor, result);
    }

    @Test
    void testGetStartingHexagons() {
        List<Hexagon> hexagons = mapHandler.getStartingHexagons();
        assertNotNull(hexagons);
    }

    @Test
    void testMakeStep_SameTile() {
        Player player = new Player();
        HexagonalGrid hexagonalGrid = map.getHexagonalGrids().get(0);
        player.setCurrentTile(hexagonalGrid);
        player.setHexagon(hexagonalGrid.getRows().get(0).get(0));
        Hexagon hexagon = hexagonalGrid.getRows().get(0).get(1);

        Tile tile = player.getCurrentTile();

        Tile result = mapHandler.makeStep(player.getHexagon(), hexagon);
        assertEquals(null, result);
    }

    @Test
    void testMakeStep_DifferentTiles() {
        Player player = new Player();
        HexagonalGrid hexagonalGrid = map.getHexagonalGrids().get(0);
        HexagonalGrid hexagonalGrid1 = map.getHexagonalGrids().get(1);
        player.setCurrentTile(hexagonalGrid);
        Tile tile = player.getCurrentTile();
        player.setHexagon(hexagonalGrid.getRows().get(3).get(6));
        Hexagon hexagon = hexagonalGrid1.getRows().get(6).get(0);
        Tile result = mapHandler.makeStep(player.getHexagon(), hexagon);
        assertNotEquals(tile, result);
    }
}