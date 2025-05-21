package components.board.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import components.board.TerrainStrip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TerrainStripDeserializerTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(TerrainStrip.class, new TerrainStripDeserializer());
        objectMapper.registerModule(module);
    }

    @Test
    void testDeserialize_TerrainStripO() throws IOException {
        String json = "{ \"coordinates\": [[1, 2], [3, 4]], \"tileType\": \"O\", \"rotation\": 90 }";

        TerrainStrip terrainStrip = objectMapper.readValue(json, TerrainStrip.class);

        assertNotNull(terrainStrip);
        assertEquals(1, terrainStrip.getCoordinates().get(0)[0]);
        assertEquals(2, terrainStrip.getCoordinates().get(0)[1]);
        assertEquals(3, terrainStrip.getCoordinates().get(1)[0]);
        assertEquals(4, terrainStrip.getCoordinates().get(1)[1]);
        assertEquals("O", terrainStrip.getName());
        assertEquals(90, terrainStrip.getRotation());
    }

    @Test
    void testDeserialize_TerrainStripQ() throws IOException {
        String json = "{ \"coordinates\": [[5, 6], [7, 8]], \"tileType\": \"Q\", \"rotation\": 180 }";

        TerrainStrip terrainStrip = objectMapper.readValue(json, TerrainStrip.class);

        assertNotNull(terrainStrip);
        assertEquals(5, terrainStrip.getCoordinates().get(0)[0]);
        assertEquals(6, terrainStrip.getCoordinates().get(0)[1]);
        assertEquals(7, terrainStrip.getCoordinates().get(1)[0]);
        assertEquals(8, terrainStrip.getCoordinates().get(1)[1]);
        assertEquals("Q", terrainStrip.getName());
        assertEquals(180, terrainStrip.getRotation());
    }

    @Test
    void testDeserialize_UnexpectedTileType() {
        String json = "{ \"coordinates\": [[1, 2], [3, 4]], \"tileType\": \"X\", \"rotation\": 90 }";

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            objectMapper.readValue(json, TerrainStrip.class);
        });

        String expectedMessage = "Unexpected value: X";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}