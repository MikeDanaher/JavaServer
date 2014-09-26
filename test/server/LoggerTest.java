package server;

import fixtures.TestRouteConfig;
import org.junit.After;
import org.junit.Test;
import routes.Route;
import utilities.FileHandler;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class LoggerTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private Route logRoute = TestRouteConfig.logRoute(baseDirectory);

    @After
    public void cleanUpLogs() throws IOException {
        FileHandler.delete(logRoute.absolutePath);
    }

    @Test
    public void testLogRequest() throws IOException {
        String request = "GET / HTTP/1.1";
        Logger.log(request, logRoute);

        byte[] logBytes = FileHandler.read(logRoute.absolutePath);
        String logText = new String(logBytes, "UTF-8");

        assertThat(logText, containsString(request));
    }
}
