package ui.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MapGrid {

    private List<List<MapPolygonText>> grid;

}
