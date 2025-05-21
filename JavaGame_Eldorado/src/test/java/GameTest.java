import javafx.scene.input.KeyCode;
import matchers.InlineCssTextAreaMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.TestFx;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class GameTest extends EldoradoGameTest{

    @BeforeEach
    public void setUp(FxRobot robot){
        waitForFxEvents(5);
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
    }

    @TestFx
    public void chooseNumberOfPlayers(FxRobot robot){
        robot.clickOn("#consoleTextInput");
        robot.write("2").type(KeyCode.ENTER);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText("2"));
//        verifyThat("#player0", TextMatcher.hasText("Red"));
//        verifyThat("#player1", TextMatcher.hasText("White"));
    }

    @TestFx
    public void playerCanPlayWithCaves(FxRobot robot){
        robot.clickOn("#consoleTextInput");
        robot.write("2").type(KeyCode.ENTER);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText("Playing with caves"));
        assertDoesNotThrow(() -> robot.write("1").type(KeyCode.ENTER));
    }

    @TestFx
    public void playerCanPlayWithNoCaves(FxRobot robot){
        robot.clickOn("#consoleTextInput");
        robot.write("2").type(KeyCode.ENTER);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText("Playing with caves"));
        assertDoesNotThrow(() -> robot.write("2").type(KeyCode.ENTER));
    }

    @TestFx
    public void playerCanSeeHisPiles(FxRobot robot){
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText(
                "Draw pile size: 4,  Discard pile size: 0"
        ));
    }

    @TestFx
    public void playerCanSeeHisHand(FxRobot robot){
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText(
                "Hand"
        ));
    }

    @TestFx
    public void playerCanAvailableActions(FxRobot robot){
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText(
                "Choose Action: \n" +
                        "1. Play a card \n" +
                        "2. Play a token \n" +
                        "3. Move your playing piece \n" +
                        "4. Buy a card \n" +
                        "5. End a turn "
        ));
    }

    @TestFx
    public void playerCanPlayCard(FxRobot robot){
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        robot.write("1").type(KeyCode.ENTER);
        robot.write("1").type(KeyCode.ENTER);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText(
                "Discard pile size: 1"
        ));
    }

    @TestFx
    public void playerCanChooseTheDirectionForMovement(FxRobot robot){
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        robot.write("3").type(KeyCode.ENTER);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText(
                "Choose the direction of movement"
        ));
    }

    @TestFx
    public void playerCanBuyACard(FxRobot robot){
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        robot.write("4").type(KeyCode.ENTER);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText(
                "MARKET BOARD"
        ));
    }

    @TestFx
    public void playerCanBuyEndTurn(FxRobot robot){
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        robot.clickOn("#consoleTextInput");
        robot.write("1").type(KeyCode.ENTER);
        robot.write("5").type(KeyCode.ENTER);
        verifyThat("#consoleTextOutput", InlineCssTextAreaMatchers.hasText(
                "Choose a card to discard. 0 to stop"
        ));
    }

}
