package components.board.serialization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JarRunner {
    public static String runJar(String name) throws InterruptedException, IOException {
        Process proc = Runtime.getRuntime().exec("java -jar " + name);

        // Wait for the process to complete
        proc.waitFor();

        // Capture the process output
        InputStream in = proc.getInputStream();
        InputStream err = proc.getErrorStream();

        // Read the JSON output from the input stream
        String jsonOutput = readStream(in);

        // Optionally, read the error stream for any error messages
        String errorOutput = readStream(err);

        if (!errorOutput.isEmpty()) {
            System.err.println("Error Output: " + errorOutput);
        }

        return jsonOutput;
    }

    private static String readStream(InputStream stream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }
}
