package ui.builders;

import components.board.EndTile;
import components.board.Hexagon;
import components.board.HexagonType;
import components.board.Tile;
import ui.elements.MapEndTile;
import ui.elements.MapPolygonText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MapEndTileBuilder extends Builder {

    public static EndTile extractEndTile(List<Tile> elements) {
        return elements.stream()
                .filter(e -> e instanceof EndTile)
                .map(e -> (EndTile) e)
                .collect(Collectors.toList())
                .get(0);
    }

    public static MapEndTile setEndTile(EndTile endTile, double initX, double initY) {
        List<List<Hexagon>> hexagons = new ArrayList<>();
        hexagons.add(endTile.getEnd());
        List<Hexagon> golden = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            golden.add(new Hexagon(HexagonType.gold, -2, true));
        }
        hexagons.add(golden);
        return switch ((int) endTile.getRotation()) {
            case 120 -> rotation120(hexagons, initX, initY, endTile.getName());
            default -> rotation0(hexagons, initX, initY, endTile.getName());
        };
    }

    private static MapEndTile rotation0(List<List<Hexagon>> elements, double initX, double initY, String name) {
        List<List<MapPolygonText>> polygons = new ArrayList<>();
        double stepX = 0.0, stepY = 0.0;
        for (List<Hexagon> h : rebuildFor0(elements)) {
            polygons.add(hexagon2PolygonRowConverter(h, initX, initY, stepX, stepY, name));
            if (polygons.size() == 1) {
                stepX -= 30;
            } else {
                stepX += 90;
            }
            stepY += 50;
        }
        return new MapEndTile(polygons);
    }

    private static List<List<Hexagon>> rebuildFor0(List<List<Hexagon>> elements) {
        List<List<Hexagon>> res = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                res.add(Arrays.asList(
                        elements.get(1).get(2),
                        elements.get(1).get(1)
                ));
            } else if (i == 1) {
                res.add(Arrays.asList(
                        elements.get(0).get(2),
                        elements.get(0).get(1),
                        elements.get(1).get(0)
                ));
            } else {
                res.add(Collections.singletonList(elements.get(0).get(0)));
            }
        }
        return res;
    }

    private static MapEndTile rotation120(List<List<Hexagon>> elements, double initX, double initY, String name) {
        List<List<MapPolygonText>> polygons = new ArrayList<>();
        double stepX = 0.0, stepY = 0.0;
        for (List<Hexagon> h : rebuildFor120(elements)) {
            polygons.add(hexagon2PolygonRowConverter(h, initX, initY, stepX, stepY, name));
            if (polygons.size() == 1) {
                stepX -= 90;
            } else {
                stepX += 30;
            }
            stepY += 50;
        }
        return new MapEndTile(polygons);
    }

    private static List<List<Hexagon>> rebuildFor120(List<List<Hexagon>> elements) {
        List<List<Hexagon>> res = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                res.add(Collections.singletonList(elements.get(0).get(0)));
            } else if (i == 1) {
                res.add(Arrays.asList(
                        elements.get(0).get(1),
                        elements.get(0).get(2),
                        elements.get(1).get(0)
                ));
            } else {
                res.add(Arrays.asList(
                        elements.get(1).get(1),
                        elements.get(1).get(2)
                ));
            }
        }
        return res;
    }
}
