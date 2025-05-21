package components.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TileTest {

    @Test
    void to_String() {
        Tile tile = new Tile();
        assertEquals(tile.toString(), tile.to_String());
    }
}