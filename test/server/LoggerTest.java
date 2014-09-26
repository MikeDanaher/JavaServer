package server;

import org.junit.After;
import org.junit.Test;
import utilities.FileHandler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class LoggerTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private Path logFile;

    @After
    public void cleanUpLogs() throws IOException {
        FileHandler.delete(logFile);
    }

    @Test
    public void testLogRequest() throws IOException {
        String request = "GET / HTTP/1.1";
        logFile = Paths.get(baseDirectory + "/logs");
        Logger.log(request, logFile);

        byte[] logBytes = FileHandler.read(logFile);
        String logText = new String(logBytes, "UTF-8");

        assertThat(logText, containsString(request));
    }
}
