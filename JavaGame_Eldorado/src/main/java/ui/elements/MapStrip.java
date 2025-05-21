package ui.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MapStrip {

    private List<List<MapPolygonText>> strips;
}
