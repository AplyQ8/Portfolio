package components.board.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import components.board.EndTile;
import components.board.HexagonType;

import java.io.IOException;

public class EndTileDeserializer extends JsonDeserializer<EndTile> {
    @Override
    public EndTile deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        HexagonType type = getType(node.get("type").asText());
        int rotation = node.get("rotation").asInt();
        JsonNode coordinatesNode = node.get("coordinates");
        int q = coordinatesNode.get(0).asInt();
        int r = coordinatesNode.get(1).asInt();
        return new EndTile(type.equals(HexagonType.green), rotation, q, r);
    }

    private HexagonType getType(String type){
        return switch(type) {
            case "green" -> HexagonType.green;
            case "blue" -> HexagonType.blue;
            default -> throw new IllegalStateException("Incorrect type: " + type);
        };
    }
}
