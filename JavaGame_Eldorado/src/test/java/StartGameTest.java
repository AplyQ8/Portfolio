import javafx.scene.input.KeyCode;
import matchers.InlineCssTextAreaMatchers;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.TestFx;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChild;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class StartGameTest extends EldoradoGameTest{

    @TestFx
    public void testLoading(FxRobot robot){
        waitForFxEvents(5);
        verifyThat("#console", hasChild("#consoleTextInput"));
        verifyThat("#console", hasChild("#consoleTextOutput"));
        waitForFxEvents(5);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText("Map"));
    }

    @TestFx
    public void testInput(FxRobot robot){
        waitForFxEvents(5);
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText("Enter number of players"));
    }

    @TestFx
    public void testMapArea(FxRobot robot){
        waitForFxEvents(5);
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        verifyThat("#window", hasChild("#map"));
    }

}
