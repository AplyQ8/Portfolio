package components.board.presets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapATest {

    @Test
    void loadMap(){
        MapA mapA = new MapA();
        assertDoesNotThrow(mapA::loadMap);
    }
}