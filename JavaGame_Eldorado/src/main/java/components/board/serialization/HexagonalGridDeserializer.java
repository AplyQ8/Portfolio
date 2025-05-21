package components.board.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import components.board.HexagonalGrid;
import components.board.presets.*;

import java.io.IOException;

public class HexagonalGridDeserializer extends JsonDeserializer<HexagonalGrid> {
    @Override
    public HexagonalGrid deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        // Extract coordinates from JSON
        JsonNode coordinatesNode = node.get("coordinates");
        int q = coordinatesNode.get(0).asInt();
        int r = coordinatesNode.get(1).asInt();

        // Extract rotation
        int rotation = node.get("rotation").asInt();
        String name = node.get("tileType").asText();

        HexagonalGrid hexagonalGrid = getPreset(name);
        hexagonalGrid.setCoordinates(q, r);
        hexagonalGrid.setRotation(rotation);
        hexagonalGrid.setNeighbours();

        // Deserialize other properties if needed
        // For example, if there are other properties in the HexagonalGrid class, set them here.

        return hexagonalGrid;
    }

    private HexagonalGrid getPreset(String type){
        HexagonalGrid hexagonalGrid = new HexagonalGrid();
        switch (type){
            case "A":
                HexagonGridA hexagonGridA = new HexagonGridA();
                hexagonalGrid = hexagonGridA.HexagonalGridA;
                hexagonalGrid.setName("A");
                break;
            case "C":
                HexagonGridC hexagonGridC = new HexagonGridC();
                hexagonalGrid = hexagonGridC.HexagonalGridC;
                hexagonalGrid.setName("C");
                break;
            case "E":
                HexagonGridE hexagonGridE = new HexagonGridE();
                hexagonalGrid = hexagonGridE.HexagonalGridE;
                hexagonalGrid.setName("E");
                break;
            case "G":
                HexagonGridG hexagonGridG = new HexagonGridG();
                hexagonalGrid = hexagonGridG.HexagonalGridG;
                hexagonalGrid.setName("G");
                break;
            case "I":
                HexagonGridI hexagonGridI = new HexagonGridI();
                hexagonalGrid = hexagonGridI.HexagonalGridI;
                hexagonalGrid.setName("I");
                break;
            case "K":
                HexagonGridK hexagonGridK = new HexagonGridK();
                hexagonalGrid = hexagonGridK.HexagonalGridK;
                hexagonalGrid.setName("K");
                break;
            case "M":
                HexagonGridM hexagonGridM = new HexagonGridM();
                hexagonalGrid = hexagonGridM.HexagonalGridM;
                hexagonalGrid.setName("M");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return hexagonalGrid;
    }
}
