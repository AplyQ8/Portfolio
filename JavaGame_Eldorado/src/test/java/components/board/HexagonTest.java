package components.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HexagonTest {

    @Test
    public void testMoveValue() {
        Hexagon hex = new Hexagon(HexagonType.green, 2);
        assertEquals(2, hex.getValue(), "The move value should be 2");
    }

    @Test
    public void testHexagonType() {
        Hexagon hex = new Hexagon(HexagonType.green, 1);
        assertEquals(HexagonType.green, hex.getType(), "The hexagon type should be GREEN");
    }

    @Test
    void setNeighbor() {
        Hexagon hex1 = new Hexagon(HexagonType.green, 1);
        Hexagon hex2 = new Hexagon(HexagonType.red, 1);
        hex1.setNeighbor(0,hex2);
        assertEquals(hex1.getNeighbors()[0], hex2);
        assertEquals(hex2.getNeighbors()[3], hex1);
    }

    @Test
    void setIllegalNeighbor() {
        Hexagon hex1 = new Hexagon(HexagonType.green, 1);
        Hexagon hex2 = new Hexagon(HexagonType.red, 1);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> hex1.setNeighbor(6,hex2),"Incorrect neighbour number");
    }

    @Test
    void display() {
        Hexagon hex1 = new Hexagon(HexagonType.green, 1);
        String res = hex1.to_String();
        Assertions.assertEquals("Hexagon Type: green, Move Value: 1", res);
    }

    @Test
    void setHexCoordinates(){
        Hexagon hex = new Hexagon(HexagonType.green, 1, false, 0, 1);
        assertEquals(hex.getQCoord(), 0);
        assertEquals(hex.getRCoord(), 1);
    }

    @Test
    void blockadeTest(){
        Hexagon hex = new Hexagon(HexagonType.green, 1, false, 0, 0);
        hex.setBlockade();
        assertTrue(hex.isBlockade());
        hex.unsetBlockade();
        assertFalse(hex.isBlockade());
    }

    @Test
    void hexNeighboursCoordinatesTest(){
        Hexagon hex = new Hexagon(HexagonType.green, 1, false, 0, 0);
        hex.setNeighbor(0, new Hexagon(HexagonType.green, 1, false));
        hex.setNeighbor(1, new Hexagon());
        hex.setNeighbor(2, new Hexagon());
        hex.setNeighbor(3, new Hexagon());
        hex.setNeighbor(4, new Hexagon());
        hex.setNeighbor(5, new Hexagon());
        assertEquals(hex.getNeighbors()[0].getQCoord(), 1);
        assertEquals(hex.getNeighbors()[0].getRCoord(), -1);
        assertEquals(hex.getNeighbors()[1].getQCoord(), 1);
        assertEquals(hex.getNeighbors()[1].getRCoord(), 0);
        assertEquals(hex.getNeighbors()[2].getQCoord(), 0);
        assertEquals(hex.getNeighbors()[2].getRCoord(), 1);
        assertEquals(hex.getNeighbors()[3].getQCoord(), -1);
        assertEquals(hex.getNeighbors()[3].getRCoord(), 1);
        assertEquals(hex.getNeighbors()[4].getQCoord(), -1);
        assertEquals(hex.getNeighbors()[4].getRCoord(), 0);
        assertEquals(hex.getNeighbors()[5].getQCoord(), 0);
        assertEquals(hex.getNeighbors()[5].getRCoord(), -1);
    }

}