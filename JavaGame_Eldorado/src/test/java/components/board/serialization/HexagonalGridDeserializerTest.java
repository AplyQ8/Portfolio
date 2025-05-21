package components.board.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import components.board.HexagonalGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HexagonalGridDeserializerTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(HexagonalGrid.class, new HexagonalGridDeserializer());
        objectMapper.registerModule(module);
    }

    @Test
    void testDeserialize_HexagonGridA() throws IOException {
        String json = "{ \"coordinates\": [1, 2], \"rotation\": 90, \"tileType\": \"A\" }";

        HexagonalGrid hexagonalGrid = objectMapper.readValue(json, HexagonalGrid.class);

        assertNotNull(hexagonalGrid);
        assertEquals(1, hexagonalGrid.getCoordinates()[0]);
        assertEquals(2, hexagonalGrid.getCoordinates()[1]);
        assertEquals(90, hexagonalGrid.getRotation());
        assertEquals("A", hexagonalGrid.getName());
    }

    @Test
    void testDeserialize_HexagonGridC() throws IOException {
        String json = "{ \"coordinates\": [3, 4], \"rotation\": 180, \"tileType\": \"C\" }";

        HexagonalGrid hexagonalGrid = objectMapper.readValue(json, HexagonalGrid.class);

        assertNotNull(hexagonalGrid);
        assertEquals(3, hexagonalGrid.getCoordinates()[0]);
        assertEquals(4, hexagonalGrid.getCoordinates()[1]);
        assertEquals(180, hexagonalGrid.getRotation());
        assertEquals("C", hexagonalGrid.getName());
    }

    @Test
    void testDeserialize_UnexpectedTileType() {
        String json = "{ \"coordinates\": [1, 2], \"rotation\": 90, \"tileType\": \"X\" }";

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            objectMapper.readValue(json, HexagonalGrid.class);
        });

        String expectedMessage = "Unexpected value: X";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}