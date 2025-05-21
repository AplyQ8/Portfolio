package ui.builders;

import components.board.Hexagon;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import ui.elements.MapPolygonText;

import java.util.ArrayList;
import java.util.List;

public class Builder {

    private static int count = 1;

    public static List<MapPolygonText> hexagon2PolygonRowConverter(List<Hexagon> hexagons, double initX, double initY, double stepX, double stepY, String name) {
        List<MapPolygonText> polygons = new ArrayList<>();
        for (Hexagon h : hexagons) {
            polygons.add(setPolygon(h, initX, initY, stepX, stepY, name));
            stepX += 60.0;
        }
        return polygons;
    }

    public static Color getColor(Hexagon h) {
        return switch (h.getType()) {
            case green -> Color.GREEN;
            case gold -> Color.GOLD;
            case black -> Color.BLACK;
            case red -> Color.RED;
            case gray -> Color.GRAY;
            case blue -> Color.BLUE;
            case empty -> Color.WHITE;
        };
    }

    private static MapPolygonText setPolygon(Hexagon h, double initX, double initY, double stepX, double stepY, String name) {
        Polygon polygon = new Polygon();
        double textX = initX + 21.0 + stepX;
        double textY = initY + stepY + 18.0;
        polygon.getPoints().addAll(getPolygon(initX, initY, stepX, stepY));
        polygon.setFill(getColor(h));
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(1);
        Text text = new Text(textX, textY, String.valueOf(h.getValue()));
        if (h.getValue() == 0) {
            text.setId(name + "INIT" + count);
            count++;
            if (count == 5) {
                count = 1;
            }
//            System.out.println("TEXT VAL0 " + text.getId());
        } else {
            if (name != null) {
                text.setId(name + h.getQCoord() + "" + h.getRCoord());
//                System.out.println("TEXT NAME!=null " + text.getId());
            } else {
                text.setId("ET" + h.getQCoord() + "" + h.getRCoord());
//                System.out.println("TEXT NAME=null " + text.getId());
            }
        }
        return new MapPolygonText(polygon, text);
    }

    private static Double[] getPolygon(double initX, double initY, double stepX, double stepY) {
        return new Double[]{
                initX + stepX, initY + stepY,
                initX + 30.0 + stepX, initY - 20.0 + stepY,
                initX + 60.0 + stepX, initY + stepY,
                initX + 60.0 + stepX, initY + 30.0 + stepY,
                initX + 30.0 + stepX, initY + 50.0 + stepY,
                initX + stepX, initY + 30.0 + stepY
        };
    }
}
