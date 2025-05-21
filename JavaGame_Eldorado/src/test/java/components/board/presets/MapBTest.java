package components.board.presets;

import components.board.Coordinate;
import components.board.Hexagon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapBTest {

    @Test
    void loadMap(){
        MapB mapB = new MapB();
        assertDoesNotThrow(mapB::loadMap);
        Coordinate coordinate = new Coordinate(13, -12);
        for (Hexagon hex : mapB.map.getMap().get(coordinate).getNeighbors()){
            assertTrue(hex.isActive());
        }
    }
}
