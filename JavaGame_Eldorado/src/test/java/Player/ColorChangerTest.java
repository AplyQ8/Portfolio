package Player;

import components.player.ColorChanger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColorChangerTest {
    private ColorChanger colorChanger;

    @BeforeEach
    public void setUp() {
        colorChanger = ColorChanger.getInstance();
    }

    @Test
    public void testSingletonInstance() {
        ColorChanger instance1 = ColorChanger.getInstance();
        ColorChanger instance2 = ColorChanger.getInstance();
        assertSame(instance1, instance2, "ColorChanger instances should be the same (singleton)");
    }

    @Test
    public void testColorCodeGreen() {
        assertEquals("\u001B[32m", colorChanger.colorCode("green"), "Green color code should be \\u001B[32m");
    }

    @Test
    public void testColorCodeBlue() {
        assertEquals("\u001B[34m", colorChanger.colorCode("blue"), "Blue color code should be \\u001B[34m");
    }

    @Test
    public void testColorCodePurple() {
        assertEquals("\u001B[35m", colorChanger.colorCode("purple"), "Purple color code should be \\u001B[35m");
    }

    @Test
    public void testColorCodeYellow() {
        assertEquals("\u001B[33m", colorChanger.colorCode("yellow"), "Yellow color code should be \\u001B[33m");
    }

    @Test
    public void testColorCodeDefault() {
        assertEquals("\u001B[0m", colorChanger.colorCode("default"), "Default color code should be \\u001B[0m");
    }

    @Test
    public void testColorCodeUnknown() {
        assertEquals("\u001B[0m", colorChanger.colorCode("unknownColor"), "Unknown color should return default color code \\u001B[0m");
    }

    @Test
    public void testColorCodeCaseInsensitive() {
        assertEquals("\u001B[32m", colorChanger.colorCode("Green"), "Color code should be case insensitive for 'Green'");
        assertEquals("\u001B[34m", colorChanger.colorCode("BLUE"), "Color code should be case insensitive for 'BLUE'");
        assertEquals("\u001B[35m", colorChanger.colorCode("PurPle"), "Color code should be case insensitive for 'PurPle'");
        assertEquals("\u001B[33m", colorChanger.colorCode("yElLoW"), "Color code should be case insensitive for 'yElLoW'");
    }

}
