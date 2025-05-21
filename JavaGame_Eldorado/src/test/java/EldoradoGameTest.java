import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import javafx.scene.Node;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@ExtendWith(ApplicationExtension.class)
class EldoradoGameTest {
    protected Application application;
    protected ExecutorService executorService;
    private static Stage primaryStage;

    @BeforeEach
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        application = FxToolkit.setupApplication(EldoradoGame.class);
        executorService =  Executors.newSingleThreadExecutor();

    }

    public <T extends Node> T find(FxRobot robot, final String query, Class<T> nodeClass) {
        Node node = robot.lookup(query).queryAll().iterator().next();
        if (nodeClass.isInstance(node)) {
            return nodeClass.cast(node);
        } else {
            throw new ClassCastException(
                    "Cannot cast " + node.getClass().getName() + " to " + nodeClass.getName());
        }
    }


    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
        FxToolkit.cleanupAfterTest(new FxRobot(), application);
        FxToolkit.cleanupApplication(application);
        executorService.shutdownNow();
    }


}