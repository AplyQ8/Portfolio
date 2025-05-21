package components.board.presets;

import components.board.Hexagon;
import components.board.HexagonType;
import components.board.HexagonalGrid;

import java.util.Arrays;
import java.util.List;

import static components.board.presets.PresetCreator.createRow;

public class HexagonGridC {
    public HexagonalGrid HexagonalGridC = new HexagonalGrid(createHexagonGrid());

    private static List<List<Hexagon>> createHexagonGrid() {
        return Arrays.asList(
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.gray, HexagonType.gray},
                        new int[] {1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.gold, HexagonType.gray, HexagonType.blue, HexagonType.blue},
                        new int[] {1, 1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.blue, HexagonType.blue, HexagonType.gold, HexagonType.gold, HexagonType.gray, HexagonType.blue},
                        new int[] {1, 1, 1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.blue, HexagonType.gold, HexagonType.gray, HexagonType.black, HexagonType.blue, HexagonType.gray, HexagonType.gray},
                        new int[] {1, 1, 1, -1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.gold, HexagonType.gray, HexagonType.blue, HexagonType.blue, HexagonType.gold, HexagonType.gold},
                        new int[] {1, 1, 1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.gold, HexagonType.gray, HexagonType.green, HexagonType.gold, HexagonType.blue},
                        new int[] {1, 1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.blue, HexagonType.blue},
                        new int[] {1, 1, 1, 1})
        );
    }
}
