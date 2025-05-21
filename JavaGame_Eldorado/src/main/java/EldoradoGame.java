import components.gameManager.GameManager;
import components.gameManager.IO.IOHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.richtext.InlineCssTextArea;
import ui.MapInitializer;
import ui.MapMovementHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EldoradoGame extends Application {

    protected boolean isTesting = false; // Флаг для определения, запущено ли приложение в тестовом режиме

    public void setTesting(boolean testing) {
        isTesting = testing;
    }
    @Override
    public void start(Stage stage){
        Group mapRoot = new Group();
        MapInitializer.setGroup(mapRoot);
        MapMovementHandler.setGroup(mapRoot);
        ScrollPane sp = new ScrollPane(mapRoot);
        sp.setId("map");
        sp.setFitToWidth(false);
        TextField inputTextArea = inputAreaInitialization();
        InlineCssTextArea outputTextArea = outputAreaInitialization();
        ScrollPane outputScrollPane = scrollPaneInitialization(outputTextArea);
        // Создание вертикального контейнера для поля ввода и поля вывода
        VBox vbox = new VBox(outputScrollPane, inputTextArea);
        vbox.setId("console");

        // Создание корневого контейнера BorderPane
        BorderPane root = new BorderPane();
        root.setBottom(vbox); // Размещение вертикального контейнера по центру
        root.setCenter(sp);
        root.setId("window");
        IOHandler ioHandler = IOHandler.getInstance();
        ioHandler.setIOComponents(inputTextArea, outputTextArea);
        Scene scene = new Scene(root);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

        if (!isTesting) {
            // Запуск GameManager только если приложение не запущено в тестовом режиме
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
                GameManager gameManager = new GameManager(ioHandler);
                gameManager.startGame();
            });
        }

    }

    public TextField inputAreaInitialization(){

        TextField inputField = new TextField();
        inputField.setPromptText("Enter your input here");
        inputField.setPrefHeight(50);
        inputField.setStyle("-fx-control-inner-background: black; -fx-text-fill: white;");
        inputField.setId("consoleTextInput");
        return inputField;
    }

    public InlineCssTextArea outputAreaInitialization() {
        InlineCssTextArea outputTextArea = new InlineCssTextArea();
        outputTextArea.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        outputTextArea.setEditable(false); // Сделать поле только для чтения
        outputTextArea.setWrapText(true);
        outputTextArea.setPrefHeight(200);// Перенос текста при необходимости
        outputTextArea.setId("consoleTextOutput");
        return outputTextArea;
    }

    public ScrollPane scrollPaneInitialization(InlineCssTextArea outputTextFlow) {
        ScrollPane outputScrollPane = new ScrollPane(outputTextFlow);
        outputScrollPane.setFitToWidth(true); // Подстройка ширины под контейнер
        outputScrollPane.setFitToHeight(false); // Отключение подстройки высоты под контейнер
        return outputScrollPane;
    }
}
