package components.board.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import components.board.TerrainStrip;
import components.board.presets.TerrainStripO;
import components.board.presets.TerrainStripQ;

import java.io.IOException;

public class TerrainStripDeserializer extends JsonDeserializer<TerrainStrip> {
    @Override
    public TerrainStrip deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);

        JsonNode coords = node.get("coordinates");
        int q1 = coords.get(0).get(0).asInt();
        int r1 = coords.get(0).get(1).asInt();
        int q2 = coords.get(1).get(0).asInt();
        int r2 = coords.get(1).get(1).asInt();
        String name = node.get("tileType").asText();
        int rotation = node.get("rotation").asInt();
        TerrainStrip terrainStrip = getPreset(name);
        terrainStrip.setCoordinates(q1, r1, q2, r2);
        terrainStrip.setRotation(rotation);
        terrainStrip.setNeighbours();
        return terrainStrip;

    }

    private TerrainStrip getPreset(String name){
        TerrainStrip terrainStrip;
        switch (name){
            case "O":
                TerrainStripO terrainStripO = new TerrainStripO();
                terrainStrip = terrainStripO.TerrainStripO;
                terrainStrip.setName("O");
                break;
            case "Q":
                TerrainStripQ terrainStripQ = new TerrainStripQ();
                terrainStrip = terrainStripQ.TerrainStripQ;
                terrainStrip.setName("Q");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return terrainStrip;

    }
}
