package components.board.presets;

import components.board.Hexagon;
import components.board.HexagonType;
import components.board.TerrainStrip;

import java.util.Arrays;
import java.util.List;

import static components.board.presets.PresetCreator.createRow;

public class TerrainStripQ {
    public TerrainStrip TerrainStripQ = new TerrainStrip(createTerrainStrip());

    private static List<List<Hexagon>> createTerrainStrip() {
        return Arrays.asList(
                createRow(new HexagonType[] {HexagonType.gray, HexagonType.gold, HexagonType.gold, HexagonType.blue, HexagonType.green},
                        new int[] {1, 1, 1, 1, 3}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.gray, HexagonType.green, HexagonType.gold, HexagonType.blue, HexagonType.green},
                        new int[] {2, 1, 2, 3, 1, 2}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.gray, HexagonType.green, HexagonType.green, HexagonType.blue},
                        new int[] {1, 3, 1, 1, 2})
        );
    }
}
