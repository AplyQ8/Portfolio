package components.board.presets;

import components.board.Hexagon;
import components.board.HexagonType;
import components.board.TerrainStrip;

import java.util.Arrays;
import java.util.List;

import static components.board.presets.PresetCreator.createRow;

public class TerrainStripO {
    public TerrainStrip TerrainStripO = new TerrainStrip(createTerrainStrip());

    private static List<List<Hexagon>> createTerrainStrip() {
        return Arrays.asList(
                createRow(new HexagonType[] {HexagonType.gold, HexagonType.gold, HexagonType.gray, HexagonType.green, HexagonType.gray},
                        new int[] {2, 1, 1, 2, 2}),
                createRow(new HexagonType[] {HexagonType.gold, HexagonType.black, HexagonType.blue, HexagonType.black, HexagonType.black, HexagonType.gray},
                        new int[] {1, -1, 4, -1, -1, 1}),
                createRow(new HexagonType[] {HexagonType.gold, HexagonType.blue, HexagonType.green, HexagonType.green, HexagonType.gray},
                        new int[] {1, 1, 2, 1, 1})
        );
    }
}
