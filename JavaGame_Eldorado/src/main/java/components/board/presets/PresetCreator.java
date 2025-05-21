package components.board.presets;

import components.board.Hexagon;
import components.board.HexagonType;

import java.util.Arrays;
import java.util.List;

public class PresetCreator {
    public static List<Hexagon> createRow(HexagonType[] types, int[] values) {
        Hexagon[] row = new Hexagon[types.length];
        for (int i = 0; i < types.length; i++) {
            row[i] = new Hexagon(types[i], values[i]);
        }
        return Arrays.asList(row);
    }
}
