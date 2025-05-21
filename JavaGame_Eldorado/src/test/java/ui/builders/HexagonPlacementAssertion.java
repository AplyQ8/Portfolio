package ui.builders;

import components.board.Hexagon;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HexagonPlacementAssertion {

    public static void assertCorrectHexagonsRotation(List<List<Hexagon>> expected, List<List<Hexagon>> actual) {
        assertEquals(expected.size(), actual.size(), "Outer lists differ in size");
        for (int i = 0; i < expected.size(); i++) {
            List<Hexagon> expectedSubList = expected.get(i);
            List<Hexagon> actualSubList = actual.get(i);
            assertEquals(expectedSubList.size(), actualSubList.size(), "Sublist at index " + i + " differs in size");
            for (int j = 0; j < expectedSubList.size(); j++) {
                assertEquals(expectedSubList.get(j).getType(), actualSubList.get(j).getType(),
                        "Element at (" + i + "," + j + ") differs, expected [" + expectedSubList.get(j).getType() + "], found [" + actualSubList.get(j).getType() + "]");
                assertEquals(expectedSubList.get(j).getValue(), actualSubList.get(j).getValue(),
                        "Element at (" + i + "," + j + ") differs, expected [" + expectedSubList.get(j).getValue() + "], found [" + actualSubList.get(j).getValue() + "]");
            }
        }
    }
}
