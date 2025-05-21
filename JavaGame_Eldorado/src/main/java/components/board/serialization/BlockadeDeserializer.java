package components.board.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import components.board.Blockade;
import components.board.HexagonType;
import components.board.Tile;

import java.io.IOException;

public class BlockadeDeserializer extends JsonDeserializer<Blockade> {

    @Override
    public Blockade deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        int power = node.get("power").asInt();
        HexagonType type = getType(node.get("type").asText());
        Tile neighborA = new Tile();
        Tile neighborB = new Tile();
        neighborA.setName(node.get("between").get(0).asText());
        neighborB.setName(node.get("between").get(1).asText());
        int number = node.get("number").asInt();
        return new Blockade(power, neighborA, neighborB, type, number);
    }

    private HexagonType getType(String type){
        return switch(type) {
            case "green" -> HexagonType.green;
            case "gold" -> HexagonType.gold;
            case "black" -> HexagonType.black;
            case "red" -> HexagonType.red;
            case "blue" -> HexagonType.blue;
            case "gray" -> HexagonType.gray;
            default -> throw new IllegalStateException("Incorrect type: " + type);
        };
    }
}
