package ui;

import components.board.*;
import components.board.presets.MapB;
import ui.builders.MapBlockadeBuilder;
import ui.builders.MapEndTileBuilder;
import ui.builders.MapGridBuilder;
import ui.builders.MapStripBuilder;
import ui.elements.*;

import java.util.ArrayList;
import java.util.List;

public class MapBBuilder extends AbstractMapBuilder {
    public MapElements initMap() {
        return setMapElements(new MapB().loadMap().getElements());
    }

    public List<MapGrid> buildTiles(List<HexagonalGrid> elements) {
        List<MapGrid> polygons = new ArrayList<>();
        elements.forEach(e -> {
            switch (e.getName()) {
                case "A" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 127.0, 385.0));
                case "C" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 800.0, 334.0));
                case "K" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 830.0, -41.0));
                case "G" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 420.0, -200.0));
                case "E" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 449.0, 175.0));
            }
        });
        return polygons;
    }

    public List<MapStrip> buildStrips(List<TerrainStrip> elements) {
        List<MapStrip> polygons = new ArrayList<>();
        elements.forEach(e -> {
            if (e.getName().equals("O")) {
                polygons.add(MapStripBuilder.hexagonStripConverter(e, 735.0, -200.0));
            }
        });
        return polygons;
    }

    public List<MapBlockade> buildBlockades(List<Blockade> elements) {
        List<MapBlockade> polygons = new ArrayList<>();
        elements.forEach(e -> {
            switch (e.getNumber()) {
                case 1 -> polygons.add(MapBlockadeBuilder.setBlockade(e, 348.0, 330.0, 0.0));
                case 2 -> polygons.add(MapBlockadeBuilder.setBlockade(e, 420.0, 142.0, 303.0));
                case 4 -> polygons.add(MapBlockadeBuilder.setBlockade(e, 819.0, 311.0, 56.0));
                case 5 -> polygons.add(MapBlockadeBuilder.setBlockade(e, 831.0, 302.0, 303.0));
            }
        });
        return polygons;
    }

    public MapEndTile buildEndTile(EndTile endTile) {
        return MapEndTileBuilder.setEndTile(endTile, 975.0, -301.0);
    }
}
