package ui.builders;

import components.board.*;
import ui.elements.MapGrid;
import ui.elements.MapPolygonText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapGridBuilder extends Builder {

    public static MapGrid hexagonGridConverter(HexagonalGrid grid, double initX, double initY) {
        List<List<MapPolygonText>> polygonalGrid = new ArrayList<>();
        double stepX = 0.0, stepY = 0.0;
        for (List<Hexagon> l : grid.getRows()) {
            polygonalGrid.add(hexagon2PolygonRowConverter(l, initX, initY, stepX, stepY, grid.getName()));
            if (polygonalGrid.size() < 4) {
                stepX -= 30.0;
            } else {
                stepX += 30.0;
            }
            stepY += 50.0;
        }
        return new MapGrid(polygonalGrid);
    }

    public static List<HexagonalGrid> extractGrids(List<Tile> elements) {
        return elements.stream()
                .filter(e -> e instanceof HexagonalGrid)
                .map(e -> (HexagonalGrid) e)
                .collect(Collectors.toList());
    }
}
