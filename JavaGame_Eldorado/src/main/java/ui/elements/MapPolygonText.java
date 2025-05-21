package ui.elements;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.util.Pair;
import lombok.Getter;

@Getter
public class MapPolygonText {

    private Pair<Polygon, Text> polygon;
    private Pair<Polyline, Text> polyline;

    public MapPolygonText(Polygon p, Text t) {
        this.polygon = new Pair<>(p, t);
    }

    public MapPolygonText(Polyline p, Text t) {
        this.polyline = new Pair<>(p, t);
    }
}
