package components.board.serialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import components.board.Blockade;
import components.board.EndTile;
import components.board.HexagonalGrid;
import components.board.TerrainStrip;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TileWrapper {
    @JsonProperty("endTile")
    public EndTile endTiles;
    private ArrayList<HexagonalGrid> tiles;
    private ArrayList<TerrainStrip> strips;
    private ArrayList<Blockade> blockades;

    public TileWrapper(ArrayList<HexagonalGrid> hexagonalGrids, ArrayList<TerrainStrip> strips,
                       ArrayList<Blockade> blockades, EndTile endTiles){
        tiles = hexagonalGrids;
        this.strips = strips;
        this.blockades = blockades;
        this.endTiles = endTiles;
    }
}
