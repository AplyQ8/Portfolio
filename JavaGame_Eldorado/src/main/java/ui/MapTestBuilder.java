package ui;

import components.board.EndTile;
import components.board.HexagonalGrid;
import components.board.Tile;
import components.board.presets.MapTest;
import ui.builders.MapEndTileBuilder;
import ui.builders.MapGridBuilder;
import ui.elements.MapElements;
import ui.elements.MapEndTile;
import ui.elements.MapGrid;

import java.util.ArrayList;
import java.util.List;

public class MapTestBuilder {

    public static MapElements initMap() {
        MapTest loader = new MapTest();
        List<Tile> elements = loader.loadMap().getElements();
        return new MapElements(
                buildTiles(MapGridBuilder.extractGrids(elements)),
                buildEndTile(MapEndTileBuilder.extractEndTile(elements))
        );
    }

    private static List<MapGrid> buildTiles(List<HexagonalGrid> elements) {
        List<MapGrid> polygons = new ArrayList<>();
        elements.forEach(e -> {
            if (e.getName().equals("A")) {
                polygons.add(MapGridBuilder.hexagonGridConverter(e, 127.0, 385.0));
            }
        });
        return polygons;
    }

    private static MapEndTile buildEndTile(EndTile endTile) {
        return MapEndTileBuilder.setEndTile(endTile, 369.0, 693.0);
    }
}
