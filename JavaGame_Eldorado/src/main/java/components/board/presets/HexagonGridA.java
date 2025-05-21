package components.board.presets;

import components.board.Hexagon;
import components.board.HexagonType;
import components.board.HexagonalGrid;

import java.util.Arrays;
import java.util.List;

import static components.board.presets.PresetCreator.createRow;

public class HexagonGridA {
    public HexagonalGrid HexagonalGridA = new HexagonalGrid(createHexagonGrid());

    private static List<List<Hexagon>> createHexagonGrid() {
        return Arrays.asList(
                createRow(new HexagonType[] {HexagonType.green, HexagonType.red, HexagonType.green, HexagonType.green},
                        new int[] {1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.blue, HexagonType.black, HexagonType.green, HexagonType.green, HexagonType.gold},
                        new int[] {1, -1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.black, HexagonType.gold, HexagonType.green, HexagonType.green, HexagonType.green},
                        new int[] {1, -1, 1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.gold, HexagonType.green, HexagonType.blue, HexagonType.green, HexagonType.gold, HexagonType.green},
                        new int[] {1, 1, 1, 1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.gold, HexagonType.green, HexagonType.blue, HexagonType.green},
                        new int[] {1, 1, 1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green},
                        new int[] {1, 1, 1, 1, 1}),
                createRow(new HexagonType[] {HexagonType.green, HexagonType.green, HexagonType.green, HexagonType.green},
                        new int[] {0, 0, 0, 0})
        );
    }
}
