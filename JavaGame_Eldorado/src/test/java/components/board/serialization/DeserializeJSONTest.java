package components.board.serialization;

import components.board.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeserializeJSONTest {

    @Test
    void deserializeJsonIntoMap() {
        String file = "src/test/java/components/board/serialization/board_config.json";
        DeserializeJSON deserializeJSON = new DeserializeJSON();
        Map map = deserializeJSON.deserializeJsonIntoMap(file);
        assertNotNull(map.getEndTiles());
        assertNotNull(map.getBlockades());
        assertNotNull(map.getHexagonalGrids());
        assertNotNull(map.getTerrainStrips());
    }
}