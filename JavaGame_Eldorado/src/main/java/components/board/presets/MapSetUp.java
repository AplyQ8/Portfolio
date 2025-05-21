package components.board.presets;

import components.board.HexagonalGrid;
import components.board.Map;
import components.board.TerrainStrip;

public class MapSetUp {
    Map map;

    void setUpHexagon(HexagonalGrid hexagonalGrid, int q, int r, int rotation, String name){
        hexagonalGrid.setCoordinates(q,r);
        hexagonalGrid.setRotation(rotation);
        hexagonalGrid.setNeighbours();
        hexagonalGrid.setName(name);
        map.addElement(hexagonalGrid);
    }

    void setUpTerrainStrip(TerrainStrip terrainStrip, int q1, int r1, int q2, int r2, int rotation,
                           String name){
        terrainStrip.setCoordinates(q1, r1, q2, r2);
        terrainStrip.setRotation(rotation);
        terrainStrip.setName(name);
        terrainStrip.setNeighbours();
        map.addElement(terrainStrip);
    }

}
