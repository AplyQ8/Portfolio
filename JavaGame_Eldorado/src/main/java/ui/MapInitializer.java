package ui;

import javafx.scene.Group;
import ui.elements.MapBlockade;
import ui.elements.MapElements;
import ui.elements.MapGrid;
import ui.elements.MapStrip;

public class MapInitializer {

    private static Group root;

    public static void setGroup(Group inp) {
        root = inp;
    }

    public static void initMapA() {
        MapElements mapElements = new MapABuilder().initMap();
        setUpElements(mapElements);
    }

    public static void initMapB() {
        MapElements mapElements = new MapBBuilder().initMap();
        setUpElements(mapElements);
    }

    private static void setUpElements(MapElements mapElements) {
        for (MapGrid grid : mapElements.getGrids()) {
            grid.getGrid().forEach(r -> r.forEach(l ->
                            root.getChildren().addAll(
                                    l.getPolygon().getKey(),
                                    l.getPolygon().getValue())
                    )
            );
        }
        for (MapStrip strip : mapElements.getStrips()) {
            strip.getStrips().forEach(r -> r.forEach(l ->
                            root.getChildren().addAll(
                                    l.getPolygon().getKey(),
                                    l.getPolygon().getValue())
                    )
            );
        }
        for (MapBlockade blockade : mapElements.getBlockades()) {
            root.getChildren().addAll(
                    blockade.getBlockade().getPolyline().getKey(),
                    blockade.getBlockade().getPolyline().getValue()
            );
        }
        mapElements.getEndTile().getEndTile().forEach(r -> r.forEach(l ->
                root.getChildren().addAll(
                        l.getPolygon().getKey(),
                        l.getPolygon().getValue()
                )
        ));
    }

    private static void setUpTestElements(MapElements mapElements) {
        for (MapGrid grid : mapElements.getGrids()) {
            grid.getGrid().forEach(r -> r.forEach(l ->
                            root.getChildren().addAll(
                                    l.getPolygon().getKey(),
                                    l.getPolygon().getValue())
                    )
            );
        }
        mapElements.getEndTile().getEndTile().forEach(r -> r.forEach(l ->
                root.getChildren().addAll(
                        l.getPolygon().getKey(),
                        l.getPolygon().getValue()
                )
        ));
    }
}
