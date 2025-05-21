package components.board.presets;

import components.board.*;

public class MapB extends MapSetUp{

    public MapB(){
        map = new Map();
        map.initializeMap();
    }

    public Map loadMap(){
        HexagonGridA hexagonGridA = new HexagonGridA();
        setUpHexagon(hexagonGridA.HexagonalGridA, 0,0, 0, "A");

        HexagonGridE hexagonGridE = new HexagonGridE();
        setUpHexagon(hexagonGridE.HexagonalGridE, 7, -4, 300, "E");

        Blockade blockadeAE = new Blockade(1, hexagonGridA.HexagonalGridA, hexagonGridE.HexagonalGridE, HexagonType.green,
                1);
        map.addElement(blockadeAE);

        HexagonGridG hexagonGridG = new HexagonGridG();
        setUpHexagon(hexagonGridG.HexagonalGridG, 10, -11, 120, "G");

        Blockade blockadeEG = new Blockade(1, hexagonGridE.HexagonalGridE, hexagonGridG.HexagonalGridG, HexagonType.gold,
                2);
        map.addElement(blockadeEG);

        HexagonGridC hexagonGridC = new HexagonGridC();
        setUpHexagon(hexagonGridC.HexagonalGridC, 11, -1, 0, "C");

        Blockade blockadeEC = new Blockade(1, hexagonGridE.HexagonalGridE, hexagonGridC.HexagonalGridC, HexagonType.blue,
                4);
        map.addElement(blockadeEC);

        HexagonGridK hexagonGridK = new HexagonGridK();
        setUpHexagon(hexagonGridK.HexagonalGridK, 15, -8, 0, "K");

        Blockade blockadeCK = new Blockade(1, hexagonGridC.HexagonalGridC, hexagonGridK.HexagonalGridK, HexagonType.green,
                5);
        map.addElement(blockadeCK);

        TerrainStripO terrainStripO = new TerrainStripO();
        setUpTerrainStrip(terrainStripO.TerrainStripO, 17, -13, 16, -13, 180, "O");

        EndTile endTile = new EndTile(false, 0, 20, -15);
        map.addElement(endTile);

        map.launchMap();
        return map;
    }
}
