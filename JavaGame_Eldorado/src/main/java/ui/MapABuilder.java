package ui;

import components.board.*;
import components.board.presets.MapA;
import ui.builders.MapBlockadeBuilder;
import ui.builders.MapEndTileBuilder;
import ui.builders.MapStripBuilder;
import ui.elements.*;
import ui.builders.MapGridBuilder;

import java.util.ArrayList;
import java.util.List;

public class MapABuilder extends AbstractMapBuilder {

    public MapElements initMap() {
        return setMapElements(new MapA().loadMap().getElements());
    }

    public List<MapGrid> buildTiles(List<HexagonalGrid> elements) {
        List<MapGrid> polygons = new ArrayList<>();
        elements.forEach(e -> {
            switch (e.getName()) {
                case "A" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 127.0, 385.0));
                case "I" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 478.0, 225.0));
                case "C" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 795.0, 15.0));
                case "M" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 1145.0, -145.0));
                case "K" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 766.0, 486.0));
                case "G" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 1234.0, 230.0));
                case "E" -> polygons.add(MapGridBuilder.hexagonGridConverter(e, 1615.0, 120.0));
            }
        });
        return polygons;
    }

    public List<MapStrip> buildStrips(List<TerrainStrip> elements) {
        List<MapStrip> polygons = new ArrayList<>();
        elements.forEach(e -> {
            switch (e.getName()) {
                case "Q" -> polygons.add(MapStripBuilder.hexagonStripConverter(e, 1015.0, 380.0));
                case "O" -> polygons.add(MapStripBuilder.hexagonStripConverter(e, 1518.0, 482.0));
            }
        });
        return polygons;
    }

    public List<MapBlockade> buildBlockades(List<Blockade> elements) {
        List<MapBlockade> polygons = new ArrayList<>();
        elements.forEach(e -> {
            switch (e.getNumber()) {
                case 1 -> polygons.add(MapBlockadeBuilder.setBlockade(e, 1045.0, 10.0, 0.0));
                case 2 -> polygons.add(MapBlockadeBuilder.setBlockade(e, 699.0, 170.0, 0.0));
                case 3 -> polygons.add(MapBlockadeBuilder.setBlockade(e, 377.0, 380.0, 0.0));
                case 4 -> polygons.add(MapBlockadeBuilder.setBlockade(e, 785.0, 463.0, 56.0));
                case 5 -> polygons.add(MapBlockadeBuilder.setBlockade(e, 1485.0, 225.0, 0.0));
                case 6 -> polygons.add(MapBlockadeBuilder.setBlockade(e, 1472.0, 217.0, 123.0));
            }
        });
        return polygons;
    }

    public MapEndTile buildEndTile(EndTile endTile) {
        return MapEndTileBuilder.setEndTile(endTile, 1818.0, 583.0);
    }
}
