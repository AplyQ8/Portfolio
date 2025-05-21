package ui.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MapElements {

    private List<MapGrid> grids;
    private List<MapStrip> strips;
    private List<MapBlockade> blockades;
    private MapEndTile endTile;

    public MapElements(List<MapGrid> grids, MapEndTile endTile) {
        this.grids = grids;
        this.endTile = endTile;
    }
}
