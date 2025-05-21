package components.board.presets;

import components.board.Hexagon;
import components.board.HexagonType;
import components.board.HexagonalGrid;

import java.util.Arrays;
import java.util.List;

import static components.board.presets.PresetCreator.createRow;

public class HexagonGridI {
    public HexagonalGrid HexagonalGridI = new HexagonalGrid(createHexagonGrid());

    private static List<List<Hexagon>> createHexagonGrid() {
        return Arrays.asList(
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green},
                        new int[] {1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.gold, HexagonType.green, HexagonType.black, HexagonType.green, HexagonType.green},
                        new int[] {1, 1, -1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.gold, HexagonType.gold, HexagonType.green, HexagonType.black, HexagonType.green, HexagonType.green},
                        new int[] {1, 2, 1, -1, 2, 1}),
                createRow(new HexagonType[] {HexagonType.gold, HexagonType.gold, HexagonType.green, HexagonType.green, HexagonType.red, HexagonType.black, HexagonType.black},
                        new int[] {1, 2, 1, 2, 3, -1, -1}),
                createRow(new HexagonType[] {HexagonType.gold, HexagonType.gray, HexagonType.black, HexagonType.black, HexagonType.green, HexagonType.green},
                        new int[] {2, 3, -1, -1, 2, 1}),
                createRow(new HexagonType[] {HexagonType.blue, HexagonType.blue, HexagonType.blue, HexagonType.green, HexagonType.green},
                        new int[] {2, 1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.blue, HexagonType.blue, HexagonType.blue, HexagonType.green},
                        new int[] {2, 2, 1, 1})
        );
    }
}
