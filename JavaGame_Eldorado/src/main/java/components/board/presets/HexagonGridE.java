package components.board.presets;

import components.board.Hexagon;
import components.board.HexagonType;
import components.board.HexagonalGrid;

import java.util.Arrays;
import java.util.List;

import static components.board.presets.PresetCreator.createRow;

public class HexagonGridE {
    public HexagonalGrid HexagonalGridE = new HexagonalGrid(createHexagonGrid());

    private static List<List<Hexagon>> createHexagonGrid() {
        return Arrays.asList(
                createRow(new HexagonType[] {HexagonType.red, HexagonType.gold, HexagonType.gold, HexagonType.gold},
                        new int[] {1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.black, HexagonType.gold},
                        new int[] {1, 2, 1, -1, 1}),
                createRow(new HexagonType[] {HexagonType.gray, HexagonType.green, HexagonType.black, HexagonType.blue, HexagonType.blue, HexagonType.green},
                        new int[] {1, 1, -1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.blue, HexagonType.green, HexagonType.green, HexagonType.gray},
                        new int[] {1, 2, 3, 1, 1, 2, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.gray, HexagonType.gray, HexagonType.gray, HexagonType.black, HexagonType.green},
                        new int[] {1, 1, 3, 1, -1, 1}),
                createRow(new HexagonType[] {HexagonType.gray, HexagonType.black, HexagonType.green, HexagonType.blue, HexagonType.green},
                        new int[] {1, -1, 2, 2, 1}),
                createRow(new HexagonType[] {HexagonType.black, HexagonType.gray, HexagonType.gray, HexagonType.green},
                        new int[] {-1, 1, 1, 1})
        );
    }
}
