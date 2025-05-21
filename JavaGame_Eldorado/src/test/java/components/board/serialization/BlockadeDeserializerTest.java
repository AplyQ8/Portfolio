package components.board.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import components.board.Blockade;
import components.board.HexagonType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BlockadeDeserializerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Blockade.class, new BlockadeDeserializer());
        objectMapper.registerModule(module);
    }

    @Test
    void testDeserialize_ValidBlockade() throws IOException {
        String json = "{ \"power\": 5, \"type\": \"green\", \"between\": [\"TileA\", \"TileB\"], \"number\": 1 }";

        Blockade blockade = objectMapper.readValue(json, Blockade.class);

        assertNotNull(blockade);
        assertEquals(5, blockade.getPower());
        assertEquals(HexagonType.green, blockade.getType());
        assertEquals("TileA", blockade.getNeighbourA().getName());
        assertEquals("TileB", blockade.getNeighbourB().getName());
        assertEquals(1, blockade.getNumber());
    }

    @Test
    void testDeserialize_AnotherValidBlockade() throws IOException {
        String json = "{ \"power\": 10, \"type\": \"blue\", \"between\": [\"TileX\", \"TileY\"], \"number\": 2 }";

        Blockade blockade = objectMapper.readValue(json, Blockade.class);

        assertNotNull(blockade);
        assertEquals(10, blockade.getPower());
        assertEquals(HexagonType.blue, blockade.getType());
        assertEquals("TileX", blockade.getNeighbourA().getName());
        assertEquals("TileY", blockade.getNeighbourB().getName());
        assertEquals(2, blockade.getNumber());
    }

    @Test
    void testDeserialize_UnexpectedHexagonType() {
        String json = "{ \"power\": 5, \"type\": \"unknown\", \"between\": [\"TileA\", \"TileB\"], \"number\": 1 }";

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            objectMapper.readValue(json, Blockade.class);
        });

        String expectedMessage = "Incorrect type: unknown";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}