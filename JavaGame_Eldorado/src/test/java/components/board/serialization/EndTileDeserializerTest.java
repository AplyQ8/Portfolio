package components.board.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import components.board.EndTile;
import components.board.HexagonType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EndTileDeserializerTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(EndTile.class, new EndTileDeserializer());
        objectMapper.registerModule(module);
    }

    @Test
    void testDeserialize_GreenEndTile() throws IOException {
        String json = "{ \"type\": \"green\", \"rotation\": 90, \"coordinates\": [1, 2] }";

        EndTile endTile = objectMapper.readValue(json, EndTile.class);

        assertNotNull(endTile);
        assertSame(endTile.getType(), HexagonType.green);
        assertEquals(90, endTile.getRotation());
        assertEquals(1, endTile.getCoordinates()[0]);
        assertEquals(2, endTile.getCoordinates()[1]);
    }

    @Test
    void testDeserialize_BlueEndTile() throws IOException {
        String json = "{ \"type\": \"blue\", \"rotation\": 180, \"coordinates\": [3, 4] }";

        EndTile endTile = objectMapper.readValue(json, EndTile.class);

        assertNotNull(endTile);
        assertEquals(180, endTile.getRotation());
        assertEquals(3, endTile.getCoordinates()[0]);
        assertEquals(4, endTile.getCoordinates()[1]);
    }

    @Test
    void testDeserialize_UnexpectedHexagonType() {
        String json = "{ \"type\": \"red\", \"rotation\": 90, \"coordinates\": [1, 2] }";

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            objectMapper.readValue(json, EndTile.class);
        });

        String expectedMessage = "Incorrect type: red";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}