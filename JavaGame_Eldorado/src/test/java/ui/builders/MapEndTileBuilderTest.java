package ui.builders;

import components.board.*;
import org.junit.jupiter.api.Test;
import ui.elements.MapEndTile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
public class MapEndTileBuilderTest {

    @Test
    public void testExtractEndTile() {
        List<Tile> list = Arrays.asList(
                new Blockade(),
                new Blockade(),
                new Tile(),
                new EndTile()
        );
        assertNotNull(MapEndTileBuilder.extractEndTile(list));
    }

    private List<List<Hexagon>> getExampleList() {
        return Arrays.asList(
                Arrays.asList(
                        new Hexagon(HexagonType.blue, 1),
                        new Hexagon(HexagonType.blue, 2),
                        new Hexagon(HexagonType.blue, 3)
                ),
                Arrays.asList(
                        new Hexagon(HexagonType.gold, 4),
                        new Hexagon(HexagonType.gold, 5),
                        new Hexagon(HexagonType.gold, 6)
                )
        );
    }

    @Test
    public void testRebuildFor0() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<List<Hexagon>> toBeTested = (List<List<Hexagon>>) getRebuildFor0Method().invoke(null, getExampleList());
        assertEquals(HexagonType.gold, toBeTested.get(0).get(0).getType());
        assertEquals(HexagonType.gold, toBeTested.get(0).get(1).getType());
        assertEquals(HexagonType.blue, toBeTested.get(1).get(0).getType());
        assertEquals(HexagonType.blue, toBeTested.get(1).get(1).getType());
        assertEquals(HexagonType.gold, toBeTested.get(1).get(2).getType());
        assertEquals(HexagonType.blue, toBeTested.get(2).get(0).getType());
    }

    private Method getRebuildFor0Method() throws NoSuchMethodException {
        Method method = MapEndTileBuilder.class.getDeclaredMethod("rebuildFor0", List.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testRebuildFor120() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<List<Hexagon>> toBeTested = (List<List<Hexagon>>) getRebuildFor120Method().invoke(null, getExampleList());
        assertEquals(HexagonType.blue, toBeTested.get(0).get(0).getType());
        assertEquals(HexagonType.blue, toBeTested.get(1).get(0).getType());
        assertEquals(HexagonType.blue, toBeTested.get(1).get(1).getType());
        assertEquals(HexagonType.gold, toBeTested.get(1).get(2).getType());
        assertEquals(HexagonType.gold, toBeTested.get(2).get(0).getType());
        assertEquals(HexagonType.gold, toBeTested.get(2).get(1).getType());
    }

    private Method getRebuildFor120Method() throws NoSuchMethodException {
        Method method = MapEndTileBuilder.class.getDeclaredMethod("rebuildFor120", List.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testRotation0() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MapEndTile toBeTested = (MapEndTile) getRotation0Method().invoke(null, getExampleList(), 10.0, 10.0, null);
        assertEquals(3, toBeTested.getEndTile().size());
        assertEquals(2, toBeTested.getEndTile().get(0).size());
        assertEquals(3, toBeTested.getEndTile().get(1).size());
        assertEquals(1, toBeTested.getEndTile().get(2).size());
        assertIterableEquals(Arrays.asList(
                10.0, 10.0,
                40.0, -10.0,
                70.0, 10.0,
                70.0, 40.0,
                40.0, 60.0,
                10.0, 40.0
        ), toBeTested.getEndTile().get(0).get(0).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                70.0, 10.0,
                100.0, -10.0,
                130.0, 10.0,
                130.0, 40.0,
                100.0, 60.0,
                70.0, 40.0
        ), toBeTested.getEndTile().get(0).get(1).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                -20.0, 60.0,
                10.0, 40.0,
                40.0, 60.0,
                40.0, 90.0,
                10.0, 110.0,
                -20.0, 90.0
        ), toBeTested.getEndTile().get(1).get(0).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                40.0, 60.0,
                70.0, 40.0,
                100.0, 60.0,
                100.0, 90.0,
                70.0, 110.0,
                40.0, 90.0
        ), toBeTested.getEndTile().get(1).get(1).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                100.0, 60.0,
                130.0, 40.0,
                160.0, 60.0,
                160.0, 90.0,
                130.0, 110.0,
                100.0, 90.0
        ), toBeTested.getEndTile().get(1).get(2).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                70.0, 110.0,
                100.0, 90.0,
                130.0, 110.0,
                130.0, 140.0,
                100.0, 160.0,
                70.0, 140.0
        ), toBeTested.getEndTile().get(2).get(0).getPolygon().getKey().getPoints());
    }

    private Method getRotation0Method() throws NoSuchMethodException {
        Method method = MapEndTileBuilder.class.getDeclaredMethod("rotation0", List.class, double.class, double.class, String.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testRotation120() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MapEndTile toBeTested = (MapEndTile) getRotation120Method().invoke(null, getExampleList(), 10.0, 10.0, null);
        assertEquals(3, toBeTested.getEndTile().size());
        assertEquals(1, toBeTested.getEndTile().get(0).size());
        assertEquals(3, toBeTested.getEndTile().get(1).size());
        assertEquals(2, toBeTested.getEndTile().get(2).size());
        assertIterableEquals(Arrays.asList(
                10.0, 10.0,
                40.0, -10.0,
                70.0, 10.0,
                70.0, 40.0,
                40.0, 60.0,
                10.0, 40.0
        ), toBeTested.getEndTile().get(0).get(0).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                -80.0, 60.0,
                -50.0, 40.0,
                -20.0, 60.0,
                -20.0, 90.0,
                -50.0, 110.0,
                -80.0, 90.0
        ), toBeTested.getEndTile().get(1).get(0).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                -20.0, 60.0,
                10.0, 40.0,
                40.0, 60.0,
                40.0, 90.0,
                10.0, 110.0,
                -20.0, 90.0
        ), toBeTested.getEndTile().get(1).get(1).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                40.0, 60.0,
                70.0, 40.0,
                100.0, 60.0,
                100.0, 90.0,
                70.0, 110.0,
                40.0, 90.0
        ), toBeTested.getEndTile().get(1).get(2).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                -50.0, 110.0,
                -20.0, 90.0,
                10.0, 110.0,
                10.0, 140.0,
                -20.0, 160.0,
                -50.0, 140.0
        ), toBeTested.getEndTile().get(2).get(0).getPolygon().getKey().getPoints());
        assertIterableEquals(Arrays.asList(
                10.0, 110.0,
                40.0, 90.0,
                70.0, 110.0,
                70.0, 140.0,
                40.0, 160.0,
                10.0, 140.0
        ), toBeTested.getEndTile().get(2).get(1).getPolygon().getKey().getPoints());
    }

    private Method getRotation120Method() throws NoSuchMethodException {
        Method method = MapEndTileBuilder.class.getDeclaredMethod("rotation120", List.class, double.class, double.class, String.class);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testSetEndTile() {
        assertEquals(2, MapEndTileBuilder.setEndTile(new EndTile(false, 0, 24, 2), 10.0, 10.0).getEndTile().get(0).size());
        assertEquals(3, MapEndTileBuilder.setEndTile(new EndTile(false, 0, 24, 2), 10.0, 10.0).getEndTile().get(1).size());
        assertEquals(1, MapEndTileBuilder.setEndTile(new EndTile(false, 0, 24, 2), 10.0, 10.0).getEndTile().get(2).size());
        assertEquals(1, MapEndTileBuilder.setEndTile(new EndTile(false, 120, 24, 2), 10.0, 10.0).getEndTile().get(0).size());
        assertEquals(3, MapEndTileBuilder.setEndTile(new EndTile(false, 120, 24, 2), 10.0, 10.0).getEndTile().get(1).size());
        assertEquals(2, MapEndTileBuilder.setEndTile(new EndTile(false, 120, 24, 2), 10.0, 10.0).getEndTile().get(2).size());
    }
}
