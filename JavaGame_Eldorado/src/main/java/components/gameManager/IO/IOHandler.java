package components.gameManager.IO;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.fxmisc.richtext.InlineCssTextArea;
import ui.JavaFXStringColorAdapter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class IOHandler {
    private static IOHandler INSTANCE;
    private TextField inputTextField;
    private InlineCssTextArea outputTextArea;
    private final Object lock = new Object();
    private IOHandler() {
    }

    public static IOHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IOHandler();
        }
        return INSTANCE;
    }

    public void setIOComponents(TextField inputField, InlineCssTextArea outputTextArea) {
        this.inputTextField = inputField;
        this.outputTextArea = outputTextArea;
    }

    public String getInfoString(InfoString type) {
        return switch (type) {
            case TurnActionsMenu -> """
                    Choose Action:\s
                    1. Play a card\s
                    2. Play a token\s
                    3. Move your playing piece\s
                    4. Buy a card\s
                    5. End a turn""";
            case ChooseCardColor -> """
                    Choose a color:\s
                    1. Green\s
                    2. Blue\s
                    3. Yellow\s
                    """;
            case ChooseMovementDirection -> """
                    Choose the direction of movement. 0 to stop.\s
                    1. Top-Right\s
                    2. Right\s
                    3. Bottom-Right\s
                    4. Bottom-Left\s
                    5. Left\s
                    6. Top-Left\s
                    """;
            case CaveRulesDecision -> """
                    Playing with caves?\s
                    1. Yes\s
                    2. No""";
            case MapSelection -> """
                    Select a map:\s
                    1. Map A\s
                    2. Map B\s
                    3. Map from Jar""";
        };
    }

    public int inputIntegerInRange(String infoString, int lowerBound, int upperBound) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger result = new AtomicInteger(-1);

        Platform.runLater(() -> {
            printMessage(infoString);
            inputTextField.setEditable(true);
            inputTextField.setOnAction(e -> {
                try {
                    int inputInt = Integer.parseInt(inputTextField.getText());
                    if (inputInt >= lowerBound && upperBound >= inputInt) {
                        result.set(inputInt);
                        latch.countDown();
                    } else {
                        printMessage("Invalid Input");
                    }
                } catch (NumberFormatException ex) {
                    printMessage("Invalid Input");
                }
            });
            inputTextField.requestFocus();
            inputTextField.clear();
        });

        try {
            latch.await(); // Wait for the user input
        } catch (InterruptedException e) {
            log.warn("Something went wrong: " + e.getMessage());
        }

        return result.get();
    }


    public void printMessage(String message) {
        //Platform.runLater(() -> outputTextArea.appendText(message + "\n"));
        Platform.runLater(() -> JavaFXStringColorAdapter.updateOutputTextArea(outputTextArea, message + "\n"));
    }

    public void printTable(String title, String content) {
        printTitle(title);
        printMessage(content);
        printLine();
        printMessage(" ");
    }

    public void printTitle(String title) {
        String line = "____________________________________________________________";
        int lineSize = line.length() - 1;

        int titleLength = title.length();
        int leftPadding = (lineSize - titleLength) / 2;
        int rightPadding = lineSize - titleLength - leftPadding;

        printMessage(line);
        printMessage("|" + " ".repeat(leftPadding) + title + " ".repeat(rightPadding) + "|");
        printMessage(line);
    }

    public void printLine() {
        String line = "____________________________________________________________";
        printMessage(line);
    }

}
