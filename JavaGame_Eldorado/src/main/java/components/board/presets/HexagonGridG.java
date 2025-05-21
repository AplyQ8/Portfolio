package components.board.presets;

import components.board.Hexagon;
import components.board.HexagonType;
import components.board.HexagonalGrid;

import java.util.Arrays;
import java.util.List;

import static components.board.presets.PresetCreator.createRow;

public class HexagonGridG {
    public HexagonalGrid HexagonalGridG = new HexagonalGrid(createHexagonGrid());

    private static List<List<Hexagon>> createHexagonGrid() {
        return Arrays.asList(
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green},
                        new int[] {1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.gold, HexagonType.black, HexagonType.green},
                        new int[] {1, 2, 1, -1, 1}),
                createRow(new HexagonType[] {HexagonType.gold, HexagonType.gold, HexagonType.gold, HexagonType.gray, HexagonType.gold, HexagonType.green},
                        new int[] {1, 2, 2, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.black, HexagonType.black, HexagonType.gold, HexagonType.gold, HexagonType.gold, HexagonType.green, HexagonType.red},
                        new int[] {-1, -1, 4, 3, 2, 2, 1}),
                createRow(new HexagonType[] {HexagonType.gold, HexagonType.gold, HexagonType.gold, HexagonType.gray, HexagonType.gold, HexagonType.green},
                        new int[] {1, 2, 2, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.gold, HexagonType.black, HexagonType.green},
                        new int[] {1, 2, 1, -1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green},
                        new int[] {1, 1, 1, 1})
        );
    }
}
