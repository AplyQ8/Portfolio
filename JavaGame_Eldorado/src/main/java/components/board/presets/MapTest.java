package components.board.presets;

import components.board.Blockade;
import components.board.EndTile;
import components.board.HexagonType;
import components.board.Map;

public class MapTest extends MapSetUp{

    public MapTest(){
        map = new Map();
        map.initializeMap();
    }

    public Map loadMap(){
        HexagonGridA hexagonGridA = new HexagonGridA();
        setUpHexagon(hexagonGridA.HexagonalGridA, 0,0, 0, "A");

        EndTile endTile = new EndTile(false, 120, 0, 4);
        map.addElement(endTile);

        map.launchMap();
        return map;
    }
}
