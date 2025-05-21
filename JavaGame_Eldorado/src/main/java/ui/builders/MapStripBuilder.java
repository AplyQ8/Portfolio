package ui.builders;

import components.board.Hexagon;
import components.board.TerrainStrip;
import components.board.Tile;
import ui.elements.MapPolygonText;
import ui.elements.MapStrip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MapStripBuilder extends Builder {

    public static List<TerrainStrip> extractStrips(List<Tile> elements) {
        return elements.stream()
                .filter(e -> e instanceof TerrainStrip)
                .map(e -> (TerrainStrip) e)
                .collect(Collectors.toList());
    }

    public static MapStrip hexagonStripConverter(TerrainStrip strip, double initX, double initY) {
        return switch (strip.getRotation()) {
            case 0 -> rotation0(strip, initX, initY);
            case 60 -> rotationLeftDiagonal(strip, initX, initY);
            case 120 -> rotationRightDiagonal(strip, initX, initY);
            case 240 -> rotationLeftDiagonal(reverse(strip), initX, initY);
            case 180 -> rotation180(strip, initX, initY);
            case 300 -> rotationRightDiagonal(reverse(strip), initX, initY);
            default -> null;
        };
    }

    private static TerrainStrip reverse(TerrainStrip strip) {
        strip.getRows().forEach(Collections::reverse);
        Collections.swap(strip.getRows(), 0, 2);
        return strip;
    }

    private static MapStrip rotation0(TerrainStrip strip, double initX, double initY) {
        List<List<MapPolygonText>> polygonalGrid = new ArrayList<>();
        double stepX = 0.0, stepY = 0.0;
        for (List<Hexagon> l : strip.getRows()) {
            polygonalGrid.add(hexagon2PolygonRowConverter(l, initX, initY, stepX, stepY, strip.getName()));
            if (polygonalGrid.size() == 1) {
                stepX -= 30.0;
            } else {
                stepX += 30.0;
            }
            stepY += 50.0;
        }
        return new MapStrip(polygonalGrid);
    }

    private static MapStrip rotationLeftDiagonal(TerrainStrip strip, double initX, double initY) {
        List<List<MapPolygonText>> polygonalGrid = new ArrayList<>();
        double stepX = 0.0, stepY = 0.0;
        for (List<Hexagon> l : horizontal2VerticalLeft(strip.getRows())) {
            polygonalGrid.add(hexagon2PolygonRowConverter(l, initX, initY, stepX, stepY, strip.getName()));
            if (polygonalGrid.size() == 1) {
                stepX -= 30.0;
            } else {
                stepX += 30.0;
            }
            stepY += 50.0;
        }
        return new MapStrip(polygonalGrid);
    }

    private static MapStrip rotation180(TerrainStrip strip, double initX, double initY) {
        List<List<MapPolygonText>> polygonalGrid = new ArrayList<>();
        double stepX = 0.0, stepY = 0.0;
        for (List<Hexagon> l : reverse(strip).getRows()) {
            polygonalGrid.add(hexagon2PolygonRowConverter(l, initX, initY, stepX, stepY, strip.getName()));
            if (polygonalGrid.size() == 1) {
                stepX -= 30.0;
            } else {
                stepX += 30.0;
            }
            stepY += 50.0;
        }
        return new MapStrip(polygonalGrid);
    }

    private static MapStrip rotationRightDiagonal(TerrainStrip strip, double initX, double initY) {
        List<List<MapPolygonText>> polygonalGrid = new ArrayList<>();
        double stepX = 0.0, stepY = 0.0;
        for (List<Hexagon> l : horizontal2VerticalRight(strip.getRows())) {
            polygonalGrid.add(hexagon2PolygonRowConverter(l, initX, initY, stepX, stepY, strip.getName()));
            if (polygonalGrid.size() == 5) {
                stepX += 30.0;
            } else {
                stepX -= 30.0;
            }
            stepY += 50.0;
        }
        return new MapStrip(polygonalGrid);
    }

    private static List<List<Hexagon>> horizontal2VerticalLeft(List<List<Hexagon>> elements) {
        List<List<Hexagon>> res = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<Hexagon> row = new ArrayList<>();
            if (i == 0) {
                row.add(elements.get(1).get(i));
                row.add(elements.get(0).get(i));
                res.add(row);
            }
            if (i == 5) {
                row.add(elements.get(2).get(i - 1));
                row.add(elements.get(1).get(i));
                res.add(row);
            }
            if (i != 0 && i != 5) {
                row.add(elements.get(2).get(i - 1));
                row.add(elements.get(1).get(i));
                row.add(elements.get(0).get(i));
                res.add(row);
            }
        }
        return res;
    }

    private static List<List<Hexagon>> horizontal2VerticalRight(List<List<Hexagon>> elements) {
        List<List<Hexagon>> res = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<Hexagon> row = new ArrayList<>();
            if (i == 0) {
                row.add(elements.get(2).get(i));
                row.add(elements.get(1).get(i));
                res.add(row);
            }
            if (i == 5) {
                row.add(elements.get(1).get(i));
                row.add(elements.get(0).get(i - 1));
                res.add(row);
            }
            if (i != 0 && i != 5) {
                row.add(elements.get(2).get(i));
                row.add(elements.get(1).get(i));
                row.add(elements.get(0).get(i - 1));
                res.add(row);
            }
        }
        return res;
    }
}
