package components.board.serialization;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JarRunnerTest {

    String path = "src/test/java/components/board/serialization/";
    @Test
    void runJar(){
        assertDoesNotThrow(() -> JarRunner.runJar(path + "eldorado-team5.jar"));
        File file = new File(path + "board_config.json");
        assertTrue(file.exists());
    }

}