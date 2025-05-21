package ui.builders;

import components.board.Blockade;
import components.board.HexagonType;
import components.board.TerrainStrip;
import components.board.Tile;
import org.junit.jupiter.api.Test;
import ui.elements.MapBlockade;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapBlockadeBuilderTest {

    @Test
    public void testExtractBlockades() {
        List<Tile> elements = Arrays.asList(
                new Blockade(),
                new Blockade(),
                new Tile(),
                new Tile(),
                new Tile(),
                new TerrainStrip(new ArrayList<>())
        );
        assertEquals(2, MapBlockadeBuilder.extractBlockades(elements).size());
    }

    @Test
    public void testBuildXCoords() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Double[] toBeTested = (Double[]) getBuildXCoordsMethod().invoke(null, 10.0, 10.0);
        assertArrayEquals(new Double[] {
                10.0, 10.0, 20.0,
                20.0, 30.0, 30.0,
                40.0, 40.0, 50.0
        }, toBeTested);
    }

    private Method getBuildXCoordsMethod() throws NoSuchMethodException {
        Method method = MapBlockadeBuilder.class.getDeclaredMethod("buildXCoords", double.class, double.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testBuildYCoords() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Double[] toBeTested = (Double[]) getBuildYCoordsMethod().invoke(null, 10.0, 10.0, 5.0);
        assertArrayEquals(new Double[] {
            10.0, 20.0, 25.0,
            35.0, 40.0, 50.0,
            55.0, 65.0, 70.0,
        }, toBeTested);
    }

    private Method getBuildYCoordsMethod() throws NoSuchMethodException {
        Method method = MapBlockadeBuilder.class.getDeclaredMethod("buildYCoords", double.class, double.class, double.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testBuildCoords() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Double[] toBeTested = (Double[]) getBuildCoordsMethod().invoke(null, 10.0, 10.0, 10.0, 10.0, 5.0);
        assertArrayEquals(new Double[]{
                10.0, 10.0, 10.0, 20.0, 20.0, 25.0,
                20.0, 35.0, 30.0, 40.0, 30.0, 50.0,
                40.0, 55.0, 40.0, 65.0, 50.0, 70.0

        }, toBeTested);
    }

    private Method getBuildCoordsMethod() throws NoSuchMethodException {
        Method method = MapBlockadeBuilder.class.getDeclaredMethod("buildCoords", double.class, double.class, double.class, double.class, double.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testSetBlockade() {
        MapBlockade toBeTested0 = MapBlockadeBuilder.setBlockade(
                new Blockade(1, new Tile(), new Tile(), HexagonType.green, 6), 10.0, 10.0, 0.0);
        MapBlockade toBeTested56 = MapBlockadeBuilder.setBlockade(
                new Blockade(2, new Tile(), new Tile(), HexagonType.gray, 5), 10.0, 10.0, 56.0);
        MapBlockade toBeTested123 = MapBlockadeBuilder.setBlockade(
                new Blockade(3, new Tile(), new Tile(), HexagonType.blue, 4), 10.0, 10.0, 123.0);
        MapBlockade toBeTested303 = MapBlockadeBuilder.setBlockade(
                new Blockade(4, new Tile(), new Tile(), HexagonType.gold, 3), 10.0, 10.0, 303.0);

        assertEquals(33.0, toBeTested0.getBlockade().getPolyline().getValue().getX());
        assertEquals(66.0, toBeTested0.getBlockade().getPolyline().getValue().getY());
        assertEquals("P1\nN6", toBeTested0.getBlockade().getPolyline().getValue().getText());

        assertEquals(-28.0, toBeTested56.getBlockade().getPolyline().getValue().getX());
        assertEquals(35.0, toBeTested56.getBlockade().getPolyline().getValue().getY());
        assertEquals("P2\nN5", toBeTested56.getBlockade().getPolyline().getValue().getText());

        assertEquals(-60.0, toBeTested123.getBlockade().getPolyline().getValue().getX());
        assertEquals(3.0, toBeTested123.getBlockade().getPolyline().getValue().getY());
        assertEquals("P3\nN4", toBeTested123.getBlockade().getPolyline().getValue().getText());

        assertEquals(30.0, toBeTested303.getBlockade().getPolyline().getValue().getX());
        assertEquals(23.0, toBeTested303.getBlockade().getPolyline().getValue().getY());
        assertEquals("P4\nN3", toBeTested303.getBlockade().getPolyline().getValue().getText());
    }
}
