package components.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class EndTileTest {

    @Test
    void connectEndingTileGreen() {
        EndTile tile = new EndTile(true, 0, 0, 0);
        assertEquals(3, tile.getEnd().size());
        assertSame(tile.getEnd().get(0).getType(), HexagonType.green);
    }


    @Test
    void coordinatesTest() {
        EndTile tile = new EndTile(true, 0,-1,0);
        assertEquals(tile.getCoordinates()[0], -1);
        assertEquals(tile.getCoordinates()[1], 0);
    }
}