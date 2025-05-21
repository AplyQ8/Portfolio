package components.board.presets;

import components.board.*;

public class MapA extends MapSetUp{
    public MapA(){
        map = new Map();
        map.initializeMap();
    }

    public Map loadMap(){
        HexagonGridA hexagonGridA = new HexagonGridA();
        setUpHexagon(hexagonGridA.HexagonalGridA, 0,0, 0, "A");

        HexagonGridI hexagonGridI = new HexagonGridI();
        setUpHexagon(hexagonGridI.HexagonalGridI, 7, -3, 60, "I");

        Blockade blockadeAI = new Blockade(1, hexagonGridA.HexagonalGridA, hexagonGridI.HexagonalGridI, HexagonType.gray,
                3);
        map.addElement(blockadeAI);

        HexagonGridC hexagonGridC = new HexagonGridC();
        setUpHexagon(hexagonGridC.HexagonalGridC, 14, -7, 60, "C");

        Blockade blockadeIC = new Blockade(1, hexagonGridI.HexagonalGridI, hexagonGridC.HexagonalGridC, HexagonType.gold,
                2);
        map.addElement(blockadeIC);

        HexagonGridM hexagonGridM = new HexagonGridM();
        setUpHexagon(hexagonGridM.HexagonalGridM, 21, -10, 120, "M");

        Blockade blockadeCM = new Blockade(1, hexagonGridC.HexagonalGridC, hexagonGridM.HexagonalGridM, HexagonType.green,
                1);
        map.addElement(blockadeCM);

        HexagonGridK hexagonGridK = new HexagonGridK();
        setUpHexagon(hexagonGridK.HexagonalGridK, 9, 2, 60, "K");

        Blockade blockadeIK = new Blockade(1, hexagonGridI.HexagonalGridI, hexagonGridK.HexagonalGridK, HexagonType.blue,
                4);
        map.addElement(blockadeIK);

        TerrainStripQ terrainStripQ = new TerrainStripQ();
        setUpTerrainStrip(terrainStripQ.TerrainStripQ, 14, -1, 14, 0, 60, "Q");

        HexagonGridG hexagonGridG = new HexagonGridG();
        setUpHexagon(hexagonGridG.HexagonalGridG, 19, -3, 120, "G");

        Blockade blockadeMG = new Blockade(2, hexagonGridM.HexagonalGridM, hexagonGridG.HexagonalGridG, HexagonType.gray,
                6);
        map.addElement(blockadeMG);

        HexagonGridE hexagonGridE = new HexagonGridE();
        setUpHexagon(hexagonGridE.HexagonalGridE, 26, -5, 180, "E");

        Blockade blockadeGE = new Blockade(2, hexagonGridG.HexagonalGridG, hexagonGridE.HexagonalGridE, HexagonType.green,
                5);
        map.addElement(blockadeGE);

        TerrainStripO terrainStripO = new TerrainStripO();
        setUpTerrainStrip(terrainStripO.TerrainStripO, 22, 0, 23, 0, 0, "O");

        EndTile endTile = new EndTile(false, 120, 24, 2);
        map.addElement(endTile);

        map.launchMap();
        return map;
    }
}
