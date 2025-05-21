package components.board.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import components.board.Map;

import java.nio.file.Paths;

public class SerializeJSON {

    public void serializeIntoJsonFile(Map map) {
        TileWrapper tileWrapper = new TileWrapper(map.getHexagonalGrids(), map.getTerrainStrips(),
                map.getBlockades(), map.getEndTiles());
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get("map.json").toFile(), tileWrapper);
        }
        catch (Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }
}
