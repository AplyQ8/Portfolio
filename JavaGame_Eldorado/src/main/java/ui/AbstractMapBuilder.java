package ui;

import components.board.*;
import ui.builders.MapBlockadeBuilder;
import ui.builders.MapEndTileBuilder;
import ui.builders.MapGridBuilder;
import ui.builders.MapStripBuilder;
import ui.elements.*;

import java.util.List;

public abstract class AbstractMapBuilder {

    public MapElements setMapElements(List<Tile> elements) {
        return new MapElements(
                buildTiles(MapGridBuilder.extractGrids(elements)),
                buildStrips(MapStripBuilder.extractStrips(elements)),
                buildBlockades(MapBlockadeBuilder.extractBlockades(elements)),
                buildEndTile(MapEndTileBuilder.extractEndTile(elements))
        );
    }

    public abstract List<MapGrid> buildTiles(List<HexagonalGrid> elements);
    public abstract List<MapStrip> buildStrips(List<TerrainStrip> elements);
    public abstract List<MapBlockade> buildBlockades(List<Blockade> elements);
    public abstract MapEndTile buildEndTile(EndTile endTile);
}
