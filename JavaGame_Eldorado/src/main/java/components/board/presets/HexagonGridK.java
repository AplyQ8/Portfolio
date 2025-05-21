package components.board.presets;

import components.board.Hexagon;
import components.board.HexagonType;
import components.board.HexagonalGrid;

import java.util.Arrays;
import java.util.List;

import static components.board.presets.PresetCreator.createRow;

public class HexagonGridK {
    public HexagonalGrid HexagonalGridK = new HexagonalGrid(createHexagonGrid());

    private static List<List<Hexagon>> createHexagonGrid() {
        return Arrays.asList(
                createRow(new HexagonType[] {HexagonType.red, HexagonType.green, HexagonType.green, HexagonType.green},
                        new int[] {1, 2, 2, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.blue, HexagonType.green, HexagonType.green},
                        new int[] {1, 1, 3, 1, 2}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green},
                        new int[] {1, 2, 1, 3, 1, 2}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green},
                        new int[] {2, 1, 3, 1, 3, 2, 2}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green},
                        new int[] {2, 1, 3, 1, 2, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.gold, HexagonType.green, HexagonType.green},
                        new int[] {2, 1, 4, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.red},
                        new int[] {1, 2, 2, 1})
        );
    }
}
