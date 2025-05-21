package ui.builders;

import components.board.*;
import org.junit.jupiter.api.Test;
import ui.elements.MapStrip;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static ui.builders.HexagonPlacementAssertion.*;

@SuppressWarnings("unchecked")
public class MapStripBuilderTest {

    @Test
    public void testExtractStrips() {
        List<Tile> elements = Arrays.asList(
                new Blockade(),
                new Blockade(),
                new Tile(),
                new TerrainStrip(new ArrayList<>()),
                new Tile(),
                new Tile(),
                new TerrainStrip(new ArrayList<>())
        );
        assertEquals(2, MapStripBuilder.extractStrips(elements).size());
    }

    private List<List<Hexagon>> getExampleList() {
        return Arrays.asList(
                Arrays.asList(
                        new Hexagon(HexagonType.gray, 1),
                        new Hexagon(HexagonType.gold, 1),
                        new Hexagon(HexagonType.gold, 1),
                        new Hexagon(HexagonType.blue, 1),
                        new Hexagon(HexagonType.green, 3)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.green, 2),
                        new Hexagon(HexagonType.gray, 1),
                        new Hexagon(HexagonType.green, 2),
                        new Hexagon(HexagonType.gold, 3),
                        new Hexagon(HexagonType.blue, 1),
                        new Hexagon(HexagonType.green, 2)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.green, 1),
                        new Hexagon(HexagonType.gray, 3),
                        new Hexagon(HexagonType.green, 1),
                        new Hexagon(HexagonType.green, 1),
                        new Hexagon(HexagonType.blue, 2)
                )
        );
    }

    @Test
    public void testReverse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TerrainStrip toBeTested = (TerrainStrip) getReverseMethod().invoke(null, new TerrainStrip(getExampleList()));
        List<List<Hexagon>> reversed = Arrays.asList(
                Arrays.asList(
                        new Hexagon(HexagonType.blue, 2),
                        new Hexagon(HexagonType.green, 1),
                        new Hexagon(HexagonType.green, 1),
                        new Hexagon(HexagonType.gray, 3),
                        new Hexagon(HexagonType.green, 1)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.green, 2),
                        new Hexagon(HexagonType.blue, 1),
                        new Hexagon(HexagonType.gold, 3),
                        new Hexagon(HexagonType.green, 2),
                        new Hexagon(HexagonType.gray, 1),
                        new Hexagon(HexagonType.green, 2)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.green, 3),
                        new Hexagon(HexagonType.blue, 1),
                        new Hexagon(HexagonType.gold, 1),
                        new Hexagon(HexagonType.gold, 1),
                        new Hexagon(HexagonType.gray, 1)
                )
        );
        assertCorrectHexagonsRotation(reversed, toBeTested.getRows());
    }

    private Method getReverseMethod() throws NoSuchMethodException {
        Method method = MapStripBuilder.class.getDeclaredMethod("reverse", TerrainStrip.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testRotation0() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MapStrip toBeTested = (MapStrip) getRotation0Method().invoke(null, new TerrainStrip(getExampleList()), 10.0, 10.0);
        assertEquals(5, toBeTested.getStrips().get(0).size());
        assertEquals(6, toBeTested.getStrips().get(1).size());
        assertEquals(5, toBeTested.getStrips().get(2).size());
        assertIterableEquals(Arrays.asList(
                10.0, 10.0,
                40.0, -10.0,
                70.0, 10.0,
                70.0, 40.0,
                40.0, 60.0,
                10.0, 40.0
        ), toBeTested.getStrips().get(0).get(0).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                -20.0, 60.0,
                10.0, 40.0,
                40.0, 60.0,
                40.0, 90.0,
                10.0, 110.0,
                -20.0, 90.0
        ), toBeTested.getStrips().get(1).get(0).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                10.0, 110.0,
                40.0, 90.0,
                70.0, 110.0,
                70.0, 140.0,
                40.0, 160.0,
                10.0, 140.0
        ), toBeTested.getStrips().get(2).get(0).getPolygon().getKey().getPoints());
    }

    private Method getRotation0Method() throws NoSuchMethodException {
        Method method = MapStripBuilder.class.getDeclaredMethod("rotation0", TerrainStrip.class, double.class, double.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testHorizontal2VerticalLeft() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<List<Hexagon>> toBeTested = (List<List<Hexagon>>) getHorizontal2VerticalLeftMethod().invoke(null, getExampleList());
        List<List<Hexagon>> expected = Arrays.asList(
                Arrays.asList(
                        new Hexagon(HexagonType.green, 2),
                        new Hexagon(HexagonType.gray, 1)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.green, 1),
                        new Hexagon(HexagonType.gray, 1),
                        new Hexagon(HexagonType.gold, 1)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.gray, 3),
                        new Hexagon(HexagonType.green, 2),
                        new Hexagon(HexagonType.gold, 1)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.green, 1),
                        new Hexagon(HexagonType.gold, 3),
                        new Hexagon(HexagonType.blue, 1)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.green, 1),
                        new Hexagon(HexagonType.blue, 1),
                        new Hexagon(HexagonType.green, 3)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.blue, 2),
                        new Hexagon(HexagonType.green, 2)
                )
        );
        assertCorrectHexagonsRotation(expected, toBeTested);
    }

    private Method getHorizontal2VerticalLeftMethod() throws NoSuchMethodException {
        Method method = MapStripBuilder.class.getDeclaredMethod("horizontal2VerticalLeft", List.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testHorizontal2VerticalRight() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<List<Hexagon>> toBeTested = (List<List<Hexagon>>) getHorizontal2VerticalRightMethod().invoke(null, getExampleList());
        List<List<Hexagon>> expected = Arrays.asList(
                Arrays.asList(
                        new Hexagon(HexagonType.green, 1),
                        new Hexagon(HexagonType.green, 2)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.gray, 3),
                        new Hexagon(HexagonType.gray, 1),
                        new Hexagon(HexagonType.gray, 1)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.green, 1),
                        new Hexagon(HexagonType.green, 2),
                        new Hexagon(HexagonType.gold, 1)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.green, 1),
                        new Hexagon(HexagonType.gold, 3),
                        new Hexagon(HexagonType.gold, 1)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.blue, 2),
                        new Hexagon(HexagonType.blue, 1),
                        new Hexagon(HexagonType.blue, 1)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.green, 2),
                        new Hexagon(HexagonType.green, 3)
                )
        );
        assertCorrectHexagonsRotation(expected, toBeTested);
    }

    private Method getHorizontal2VerticalRightMethod() throws NoSuchMethodException {
        Method method = MapStripBuilder.class.getDeclaredMethod("horizontal2VerticalRight", List.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testRotationLeftDiagonal() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MapStrip toBeTested = (MapStrip) getRotationLeftDiagonalMethod().invoke(null, new TerrainStrip(getExampleList()), 10.0, 10.0);
        //Row 1, entry 1
        assertIterableEquals(Arrays.asList(
                10.0, 10.0,
                40.0, -10.0,
                70.0, 10.0,
                70.0, 40.0,
                40.0, 60.0,
                10.0, 40.0
        ), toBeTested.getStrips().get(0).get(0).getPolygon().getKey().getPoints());
        //Row 1, entry 2
        assertIterableEquals(Arrays.asList(
                70.0, 10.0,
                100.0, -10.0,
                130.0, 10.0,
                130.0, 40.0,
                100.0, 60.0,
                70.0, 40.0
        ), toBeTested.getStrips().get(0).get(1).getPolygon().getKey().getPoints());
        //Row 2, entry 1
        assertIterableEquals(Arrays.asList(
                -20.0, 60.0,
                10.0, 40.0,
                40.0, 60.0,
                40.0, 90.0,
                10.0, 110.0,
                -20.0, 90.0
        ), toBeTested.getStrips().get(1).get(0).getPolygon().getKey().getPoints());
        //Row 2, entry 2
        assertIterableEquals(Arrays.asList(
                40.0, 60.0,
                70.0, 40.0,
                100.0, 60.0,
                100.0, 90.0,
                70.0, 110.0,
                40.0, 90.0
        ), toBeTested.getStrips().get(1).get(1).getPolygon().getKey().getPoints());
        //Row 2, entry 3
        assertIterableEquals(Arrays.asList(
                100.0, 60.0,
                130.0, 40.0,
                160.0, 60.0,
                160.0, 90.0,
                130.0, 110.0,
                100.0, 90.0
        ), toBeTested.getStrips().get(1).get(2).getPolygon().getKey().getPoints());
        //Row 3, entry 1
        assertIterableEquals(Arrays.asList(
                10.0, 110.0,
                40.0, 90.0,
                70.0, 110.0,
                70.0, 140.0,
                40.0, 160.0,
                10.0, 140.0
        ), toBeTested.getStrips().get(2).get(0).getPolygon().getKey().getPoints());
        //Row 3, entry 2
        assertIterableEquals(Arrays.asList(
                70.0, 110.0,
                100.0, 90.0,
                130.0, 110.0,
                130.0, 140.0,
                100.0, 160.0,
                70.0, 140.0
        ), toBeTested.getStrips().get(2).get(1).getPolygon().getKey().getPoints());
        //Row 3, entry 3
        assertIterableEquals(Arrays.asList(
                130.0, 110.0,
                160.0, 90.0,
                190.0, 110.0,
                190.0, 140.0,
                160.0, 160.0,
                130.0, 140.0
        ), toBeTested.getStrips().get(2).get(2).getPolygon().getKey().getPoints());
        //Row 4, entry 1
        assertIterableEquals(Arrays.asList(
                40.0, 160.0,
                70.0, 140.0,
                100.0, 160.0,
                100.0, 190.0,
                70.0, 210.0,
                40.0, 190.0
        ), toBeTested.getStrips().get(3).get(0).getPolygon().getKey().getPoints());
        //Row 4, entry 2
        assertIterableEquals(Arrays.asList(
                100.0, 160.0,
                130.0, 140.0,
                160.0, 160.0,
                160.0, 190.0,
                130.0, 210.0,
                100.0, 190.0
        ), toBeTested.getStrips().get(3).get(1).getPolygon().getKey().getPoints());
        //Row 4, entry 3
        assertIterableEquals(Arrays.asList(
                160.0, 160.0,
                190.0, 140.0,
                220.0, 160.0,
                220.0, 190.0,
                190.0, 210.0,
                160.0, 190.0
        ), toBeTested.getStrips().get(3).get(2).getPolygon().getKey().getPoints());
        //Row 5, entry 1
        assertIterableEquals(Arrays.asList(
                70.0, 210.0,
                100.0, 190.0,
                130.0, 210.0,
                130.0, 240.0,
                100.0, 260.0,
                70.0, 240.0
        ), toBeTested.getStrips().get(4).get(0).getPolygon().getKey().getPoints());
        //Row 5, entry 2
        assertIterableEquals(Arrays.asList(
                130.0, 210.0,
                160.0, 190.0,
                190.0, 210.0,
                190.0, 240.0,
                160.0, 260.0,
                130.0, 240.0
        ), toBeTested.getStrips().get(4).get(1).getPolygon().getKey().getPoints());
        //Row 5, entry 3
        assertIterableEquals(Arrays.asList(
                190.0, 210.0,
                220.0, 190.0,
                250.0, 210.0,
                250.0, 240.0,
                220.0, 260.0,
                190.0, 240.0
        ), toBeTested.getStrips().get(4).get(2).getPolygon().getKey().getPoints());
        //Row 5, entry 1
        assertIterableEquals(Arrays.asList(
                100.0, 260.0,
                130.0, 240.0,
                160.0, 260.0,
                160.0, 290.0,
                130.0, 310.0,
                100.0, 290.0
        ), toBeTested.getStrips().get(5).get(0).getPolygon().getKey().getPoints());
        //Row 5, entry 2
        assertIterableEquals(Arrays.asList(
                160.0, 260.0,
                190.0, 240.0,
                220.0, 260.0,
                220.0, 290.0,
                190.0, 310.0,
                160.0, 290.0
        ), toBeTested.getStrips().get(5).get(1).getPolygon().getKey().getPoints());
    }

    private Method getRotationLeftDiagonalMethod() throws NoSuchMethodException {
        Method method = MapStripBuilder.class.getDeclaredMethod("rotationLeftDiagonal", TerrainStrip.class, double.class, double.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testRotationRightDiagonal() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MapStrip toBeTested = (MapStrip) getRotationRightDiagonalMethod().invoke(null, new TerrainStrip(getExampleList()), 10.0, 10.0);
        //Row 1, entry 1
        assertIterableEquals(Arrays.asList(
                10.0, 10.0,
                40.0, -10.0,
                70.0, 10.0,
                70.0, 40.0,
                40.0, 60.0,
                10.0, 40.0
        ), toBeTested.getStrips().get(0).get(0).getPolygon().getKey().getPoints());
        //Row 1, entry 2
        assertIterableEquals(Arrays.asList(
                70.0, 10.0,
                100.0, -10.0,
                130.0, 10.0,
                130.0, 40.0,
                100.0, 60.0,
                70.0, 40.0
        ), toBeTested.getStrips().get(0).get(1).getPolygon().getKey().getPoints());
        //Row 2, entry 1
        assertIterableEquals(Arrays.asList(
                -20.0, 60.0,
                10.0, 40.0,
                40.0, 60.0,
                40.0, 90.0,
                10.0, 110.0,
                -20.0, 90.0
        ), toBeTested.getStrips().get(1).get(0).getPolygon().getKey().getPoints());
        //Row 2, entry 2
        assertIterableEquals(Arrays.asList(
                40.0, 60.0,
                70.0, 40.0,
                100.0, 60.0,
                100.0, 90.0,
                70.0, 110.0,
                40.0, 90.0
        ), toBeTested.getStrips().get(1).get(1).getPolygon().getKey().getPoints());
        //Row 2, entry 3
        assertIterableEquals(Arrays.asList(
                100.0, 60.0,
                130.0, 40.0,
                160.0, 60.0,
                160.0, 90.0,
                130.0, 110.0,
                100.0, 90.0
        ), toBeTested.getStrips().get(1).get(2).getPolygon().getKey().getPoints());
        //Row 3, entry 1
        assertIterableEquals(Arrays.asList(
                -50.0, 110.0,
                -20.0, 90.0,
                10.0, 110.0,
                10.0, 140.0,
                -20.0, 160.0,
                -50.0, 140.0
        ), toBeTested.getStrips().get(2).get(0).getPolygon().getKey().getPoints());
        //Row 3, entry 2
        assertIterableEquals(Arrays.asList(
                10.0, 110.0,
                40.0, 90.0,
                70.0, 110.0,
                70.0, 140.0,
                40.0, 160.0,
                10.0, 140.0
        ), toBeTested.getStrips().get(2).get(1).getPolygon().getKey().getPoints());
        //Row 3, entry 3
        assertIterableEquals(Arrays.asList(
                70.0, 110.0,
                100.0, 90.0,
                130.0, 110.0,
                130.0, 140.0,
                100.0, 160.0,
                70.0, 140.0
        ), toBeTested.getStrips().get(2).get(2).getPolygon().getKey().getPoints());
        //Row 4, entry 1
        assertIterableEquals(Arrays.asList(
                -80.0, 160.0,
                -50.0, 140.0,
                -20.0, 160.0,
                -20.0, 190.0,
                -50.0, 210.0,
                -80.0, 190.0
        ), toBeTested.getStrips().get(3).get(0).getPolygon().getKey().getPoints());
        //Row 4, entry 2
        assertIterableEquals(Arrays.asList(
                -20.0, 160.0,
                10.0, 140.0,
                40.0, 160.0,
                40.0, 190.0,
                10.0, 210.0,
                -20.0, 190.0
        ), toBeTested.getStrips().get(3).get(1).getPolygon().getKey().getPoints());
        //Row 4, entry 3
        assertIterableEquals(Arrays.asList(
                40.0, 160.0,
                70.0, 140.0,
                100.0, 160.0,
                100.0, 190.0,
                70.0, 210.0,
                40.0, 190.0
        ), toBeTested.getStrips().get(3).get(2).getPolygon().getKey().getPoints());
        //Row 5, entry 1
        assertIterableEquals(Arrays.asList(
                -110.0, 210.0,
                -80.0, 190.0,
                -50.0, 210.0,
                -50.0, 240.0,
                -80.0, 260.0,
                -110.0, 240.0
        ), toBeTested.getStrips().get(4).get(0).getPolygon().getKey().getPoints());
        //Row 5, entry 2
        assertIterableEquals(Arrays.asList(
                -50.0, 210.0,
                -20.0, 190.0,
                10.0, 210.0,
                10.0, 240.0,
                -20.0, 260.0,
                -50.0, 240.0
        ), toBeTested.getStrips().get(4).get(1).getPolygon().getKey().getPoints());
        //Row 5, entry 3
        assertIterableEquals(Arrays.asList(
                10.0, 210.0,
                40.0, 190.0,
                70.0, 210.0,
                70.0, 240.0,
                40.0, 260.0,
                10.0, 240.0
        ), toBeTested.getStrips().get(4).get(2).getPolygon().getKey().getPoints());
        //Row 6, entry 1
        assertIterableEquals(Arrays.asList(
                -80.0, 260.0,
                -50.0, 240.0,
                -20.0, 260.0,
                -20.0, 290.0,
                -50.0, 310.0,
                -80.0, 290.0
        ), toBeTested.getStrips().get(5).get(0).getPolygon().getKey().getPoints());
        //Row 6, entry 2
        assertIterableEquals(Arrays.asList(
                -20.0, 260.0,
                10.0, 240.0,
                40.0, 260.0,
                40.0, 290.0,
                10.0, 310.0,
                -20.0, 290.0
        ), toBeTested.getStrips().get(5).get(1).getPolygon().getKey().getPoints());
    }

    private Method getRotationRightDiagonalMethod() throws NoSuchMethodException {
        Method method = MapStripBuilder.class.getDeclaredMethod("rotationRightDiagonal", TerrainStrip.class, double.class, double.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testRotation180() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MapStrip toBeTested = (MapStrip) getRotation180Method().invoke(null, new TerrainStrip(getExampleList()), 10.0, 10.0);
        assertEquals(5, toBeTested.getStrips().get(0).size());
        assertEquals(6, toBeTested.getStrips().get(1).size());
        assertEquals(5, toBeTested.getStrips().get(2).size());
        assertIterableEquals(Arrays.asList(
                10.0, 10.0,
                40.0, -10.0,
                70.0, 10.0,
                70.0, 40.0,
                40.0, 60.0,
                10.0, 40.0
        ), toBeTested.getStrips().get(0).get(0).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                -20.0, 60.0,
                10.0, 40.0,
                40.0, 60.0,
                40.0, 90.0,
                10.0, 110.0,
                -20.0, 90.0
        ), toBeTested.getStrips().get(1).get(0).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                10.0, 110.0,
                40.0, 90.0,
                70.0, 110.0,
                70.0, 140.0,
                40.0, 160.0,
                10.0, 140.0
        ), toBeTested.getStrips().get(2).get(0).getPolygon().getKey().getPoints());
        assertEquals(Builder.getColor(getExampleList().get(0).get(0)), toBeTested.getStrips().get(2).get(4).getPolygon().getKey().getFill());
        assertEquals(Builder.getColor(getExampleList().get(1).get(0)), toBeTested.getStrips().get(1).get(5).getPolygon().getKey().getFill());
        assertEquals(Builder.getColor(getExampleList().get(2).get(0)), toBeTested.getStrips().get(0).get(4).getPolygon().getKey().getFill());
    }

    private Method getRotation180Method() throws NoSuchMethodException {
        Method method = MapStripBuilder.class.getDeclaredMethod("rotation180", TerrainStrip.class, double.class, double.class);
        method.setAccessible(true);
        return method;
    }
}
