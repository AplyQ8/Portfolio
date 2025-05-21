package ui;

import components.gameManager.IO.IOHandler;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.InlineCssTextArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaFXStringColorAdapter {
    public static void updateOutputTextArea(InlineCssTextArea textArea, String output) {
        String[] lines = output.split("\n");
        for (String line : lines) {
            String[] parts = line.split(" ");
            for (String part : parts) {
                String color = "white";
                if (part.contains("[32m")) {
                    color = "green";
                    part = part.replace("[32m", "").replace("[0m", "");
                } else if (part.contains("[34m")) {
                    color = "blue";
                    part = part.replace("[34m", "").replace("[0m", "");
                } else if (part.contains("[33m")) {
                    color = "yellow";
                    part = part.replace("[33m", "").replace("[0m", "");
                } else if(part.contains("[35m")){
                    color = "purple";
                    part = part.replace("[35m", "").replace("[0m", "");
                } else if(part.contains("[31m")){
                    color = "red";
                    part = part.replace("[31m", "").replace("[0m", "");
                }
                int start = textArea.getLength();
                textArea.appendText(part + " ");
                textArea.setStyle(start, start + part.length() + 1, "-fx-fill: " + color + ";");
            }
            int start = textArea.getLength();
            textArea.appendText("\n");
            textArea.setStyle(start, start + 1, "-fx-fill: white;");
        }
    }


}
