package ui.builders;

import components.board.Blockade;
import components.board.HexagonalGrid;
import components.board.TerrainStrip;
import components.board.Tile;
import components.board.presets.HexagonGridA;
import org.junit.jupiter.api.Test;
import ui.elements.MapGrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class MapGridBuilderTest {

    @Test
    public void testExtractStrips() {
        List<Tile> elements = Arrays.asList(
                new Blockade(),
                new Blockade(),
                new Tile(),
                new TerrainStrip(new ArrayList<>()),
                new HexagonalGrid(),
                new HexagonalGrid(),
                new TerrainStrip(new ArrayList<>())
        );
        assertEquals(2, MapGridBuilder.extractGrids(elements).size());
    }

    @Test
    public void testHexagonalGridConverter() {
        MapGrid toBeTested = MapGridBuilder.hexagonGridConverter(new HexagonGridA().HexagonalGridA, 10.0,10.0);
        assertEquals(4, toBeTested.getGrid().get(0).size());
        assertEquals(5, toBeTested.getGrid().get(1).size());
        assertEquals(6, toBeTested.getGrid().get(2).size());
        assertEquals(7, toBeTested.getGrid().get(3).size());
        assertEquals(6, toBeTested.getGrid().get(4).size());
        assertEquals(5, toBeTested.getGrid().get(5).size());
        assertEquals(4, toBeTested.getGrid().get(6).size());
        //Row 1, entry 1
        assertIterableEquals(Arrays.asList(
                10.0, 10.0,
                40.0, -10.0,
                70.0, 10.0,
                70.0, 40.0,
                40.0, 60.0,
                10.0, 40.0
        ), toBeTested.getGrid().get(0).get(0).getPolygon().getKey().getPoints());
        //Row 2, entry 1
        assertIterableEquals(Arrays.asList(
                -20.0, 60.0,
                10.0, 40.0,
                40.0, 60.0,
                40.0, 90.0,
                10.0, 110.0,
                -20.0, 90.0
        ), toBeTested.getGrid().get(1).get(0).getPolygon().getKey().getPoints());
        //Row 3, entry 1
        assertIterableEquals(Arrays.asList(
                -50.0, 110.0,
                -20.0, 90.0,
                10.0, 110.0,
                10.0, 140.0,
                -20.0, 160.0,
                -50.0, 140.0
        ), toBeTested.getGrid().get(2).get(0).getPolygon().getKey().getPoints());
        //Row 4, entry 1
        assertIterableEquals(Arrays.asList(
                -80.0, 160.0,
                -50.0, 140.0,
                -20.0, 160.0,
                -20.0, 190.0,
                -50.0, 210.0,
                -80.0, 190.0
        ), toBeTested.getGrid().get(3).get(0).getPolygon().getKey().getPoints());
        //Row 5, entry 1
        assertIterableEquals(Arrays.asList(
                -50.0, 210.0,
                -20.0, 190.0,
                10.0, 210.0,
                10.0, 240.0,
                -20.0, 260.0,
                -50.0, 240.0
        ), toBeTested.getGrid().get(4).get(0).getPolygon().getKey().getPoints());
        //Row 6, entry 1
        assertIterableEquals(Arrays.asList(
                -20.0, 260.0,
                10.0, 240.0,
                40.0, 260.0,
                40.0, 290.0,
                10.0, 310.0,
                -20.0, 290.0
        ), toBeTested.getGrid().get(5).get(0).getPolygon().getKey().getPoints());
        //Row 7, entry 1
        assertIterableEquals(Arrays.asList(
                10.0, 310.0,
                40.0, 290.0,
                70.0, 310.0,
                70.0, 340.0,
                40.0, 360.0,
                10.0, 340.0
        ), toBeTested.getGrid().get(6).get(0).getPolygon().getKey().getPoints());
    }
}
