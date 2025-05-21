package ui.builders;

import components.board.Blockade;
import components.board.Tile;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import ui.elements.MapBlockade;
import ui.elements.MapPolygonText;

import java.util.List;
import java.util.stream.Collectors;

public class MapBlockadeBuilder extends Builder {

    public static List<Blockade> extractBlockades(List<Tile> elements) {
        return elements.stream()
                .filter(e -> e instanceof Blockade)
                .map(e -> (Blockade) e)
                .collect(Collectors.toList());
    }

    public static MapBlockade setBlockade(Blockade b, double initX, double initY, double angle) {
        Polyline polyline = new Polyline();
        Text text = new Text();
        switch ((int) angle) {
            case 56 -> {
                text.setX(initX - 38.0);
                text.setY(initY + 25.0);
                polyline.getPoints().addAll(buildCoords(initX, initY, 24.0, 36.0, 16.0));
            }
            case 123 -> {
                text.setX(initX - 70);
                text.setY(initY - 7);
                polyline.getPoints().addAll(buildCoords(initX, initY, 32.0, 35.0, 15.0));
            }
            case 303 -> {
                text.setX(initX + 20);
                text.setY(initY + 13);
                polyline.getPoints().addAll(buildCoords(initX, initY, 32.0, 35.0, 15.0));
            }
            default -> {
                text.setX(initX + 23.0);
                text.setY(initY + 56.0);
                polyline.getPoints().addAll(buildCoords(initX, initY, 30.0, 30.0, 20.0));
            }
        }
        text.setText("P" + b.getPower() + "\n" + "N" + b.getNumber());
        text.setStyle("-fx-font-weight: bold");
        polyline.setStroke(getColor(b.getBlockadeHex()));
        polyline.setStrokeWidth(20);
        if (angle != 0.0) {
            Rotate rotate = new Rotate();
            rotate.setPivotX(initX); // Set the pivot point for the rotation (x-coordinate)
            rotate.setPivotY(initY);
            rotate.setAngle(angle);
            polyline.getTransforms().add(rotate);
        }
        return new MapBlockade(new MapPolygonText(polyline, text));
    }

    public static Double[] buildCoords(double initX, double initY, double stepX, double stepY, double sideStepY) {
        Double[] xs = buildXCoords(initX, stepX);
        Double[] ys = buildYCoords(initY, stepY, sideStepY);
        Double[] res = new Double[18];
        int x = 0;
        int y = 1;
        for (int i = 0; i < 9; i++) {
            res[x] = xs[i];
            res[y] = ys[i];
            x+=2;
            y+=2;
        }
        return res;
    }

    private static Double[] buildXCoords(double initX, double stepX) {
        Double[] xs = new Double[9];
        for (int i = 0; i < 9; i++) {
            if (i < 2) {
                xs[i] = initX;
            } else if (i < 4) {
                xs[i] = initX + stepX;
            } else if (i < 6) {
                xs[i] = initX + stepX * 2;
            } else if (i < 8) {
                xs[i] = initX + stepX * 3;
            } else {
                xs[i] = initX +  stepX * 4;
            }
        }
        return xs;
    }

    private static Double[] buildYCoords(double initY, double stepY, double sideStepY) {
        Double[] ys = new Double[9];
        for (int i = 0; i < 9; i++)  {
            if (i == 0) {
                ys[i] = initY;
                continue;
            }
            if (i % 2 != 0) {
                ys[i] = ys[i - 1] + stepY;
            } else {
                ys[i] = ys[i - 1] + sideStepY;
            }
        }
        return ys;
    }
}
