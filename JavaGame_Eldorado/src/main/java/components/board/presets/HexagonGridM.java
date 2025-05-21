package components.board.presets;

import components.board.Hexagon;
import components.board.HexagonType;
import components.board.HexagonalGrid;

import java.util.Arrays;
import java.util.List;

import static components.board.presets.PresetCreator.createRow;

public class HexagonGridM {
    public HexagonalGrid HexagonalGridM = new HexagonalGrid(createHexagonGrid());

    private static List<List<Hexagon>> createHexagonGrid() {
        return Arrays.asList(
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.red},
                        new int[] {1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.gold, HexagonType.black, HexagonType.black, HexagonType.blue},
                        new int[] {1, 4, -1, -1, 4}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.gold, HexagonType.green, HexagonType.green, HexagonType.blue, HexagonType.black},
                        new int[] {1, 2, 1, 1, 1, -1}),
                createRow(new HexagonType[] {HexagonType.black, HexagonType.green, HexagonType.green, HexagonType.gray, HexagonType.green, HexagonType.green, HexagonType.black},
                        new int[] {-1, 1, 1, 2, 1, 1, -1}),
                createRow(new HexagonType[] {HexagonType.black, HexagonType.black, HexagonType.black, HexagonType.black, HexagonType.gray, HexagonType.green},
                        new int[] {-1, -1, -1, -1, 2, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.gray, HexagonType.green},
                        new int[] {1, 1, 1, 2, 1}),
                createRow(new HexagonType[] {HexagonType.blue, HexagonType.blue, HexagonType.green, HexagonType.green},
                        new int[] {1, 1, 1, 1})
        );
    }
}
