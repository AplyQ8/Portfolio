package ui.builders;

import components.board.Hexagon;
import components.board.HexagonType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.elements.MapPolygonText;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuilderTest {

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field count = Builder.class.getDeclaredField("count");
        count.setAccessible(true);
        count.setInt(null, 1);
    }

    @Test
    public void testGetPolygon() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Double[] toBeTested = (Double[]) getGetPolygonMethod().invoke(null, 10.0, 10.0, 5.0, 5.0);
        assertArrayEquals(new Double[]{
                15.0, 15.0,
                45.0, -5.0,
                75.0, 15.0,
                75.0, 45.0,
                45.0, 65.0,
                15.0, 45.0
        }, toBeTested);
        Double [] toBeTested1 = (Double[]) getGetPolygonMethod().invoke(null, 0.0, 0.0, 0.0, 0.0);
        assertArrayEquals(new Double[]{
                0.0, 0.0,
                30.0, -20.0,
                60.0, 0.0,
                60.0, 30.0,
                30.0, 50.0,
                0.0, 30.0
        }, toBeTested1);
    }

    @Test
    public void testSetPolygon() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Check Text
        MapPolygonText toBeTested = (MapPolygonText) getSetPolygonMethod().invoke(null, new Hexagon(HexagonType.green, 1, false, 1, 2),
                10.0, 10.0, 5.0, 5.0, "A");
        assertEquals(36.0, toBeTested.getPolygon().getValue().getX());
        assertEquals(33.0, toBeTested.getPolygon().getValue().getY());
        assertEquals("A12", toBeTested.getPolygon().getValue().getId());
        MapPolygonText toBeTested1 = (MapPolygonText) getSetPolygonMethod().invoke(null, new Hexagon(HexagonType.green, 0, false, 1, 2),
                10.0, 10.0, 5.0, 5.0, "A");
        assertEquals("AINIT1", toBeTested1.getPolygon().getValue().getId());
        MapPolygonText toBeTested2 = (MapPolygonText) getSetPolygonMethod().invoke(null, new Hexagon(HexagonType.green, 1, false, 1, 2),
                10.0, 10.0, 5.0, 5.0, null);
        assertEquals("ET12", toBeTested2.getPolygon().getValue().getId());

        //Check Polygon
        assertIterableEquals(Arrays.asList(
                15.0, 15.0,
                45.0, -5.0,
                75.0, 15.0,
                75.0, 45.0,
                45.0, 65.0,
                15.0, 45.0
        ), toBeTested.getPolygon().getKey().getPoints());
    }

    @Test
    public void testHexagon2PolygonRowConverter() {
        List<Hexagon> list = Arrays.asList(
                new Hexagon(HexagonType.green, 1, false, 1, 4),
                new Hexagon(HexagonType.gold, 2, false, 2, 2),
                new Hexagon(HexagonType.blue, 3, false, 3, 3),
                new Hexagon(HexagonType.black, 0, false, 4, 0),
                new Hexagon(HexagonType.empty, 5, false, 6, 1)
        );
        List<MapPolygonText> toBeTested = Builder.hexagon2PolygonRowConverter(list, 10.0, 10.0, 5.0, 5.0, "A");
        assertEquals(5, toBeTested.size());
        assertIterableEquals(Arrays.asList(
                75.0, 15.0,
                105.0, -5.0,
                135.0, 15.0,
                135.0, 45.0,
                105.0, 65.0,
                75.0, 45.0
        ), toBeTested.get(1).getPolygon().getKey().getPoints());
        assertEquals("AINIT1", toBeTested.get(3).getPolygon().getValue().getId());
    }

    private Method getGetPolygonMethod() throws NoSuchMethodException {
        Method method = Builder.class.getDeclaredMethod("getPolygon", double.class, double.class, double.class, double.class);
        method.setAccessible(true);
        return method;
    }

    private Method getSetPolygonMethod() throws NoSuchMethodException {
        Method method = Builder.class.getDeclaredMethod("setPolygon", Hexagon.class, double.class, double.class, double.class, double.class, String.class);
        method.setAccessible(true);
        return method;
    }

}