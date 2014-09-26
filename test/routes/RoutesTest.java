package routes;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import fixtures.TestRouteConfig;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class RoutesTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private List<Route> routeConfig = TestRouteConfig.getRoutes();

    @Test
    public void testDirectoryFileRoutes() throws IOException {
        Map<String, Route> validRoutes = new Routes(baseDirectory).getValidRoutes();

        assertTrue(validRoutes.containsKey("/file1"));
        assertEquals("file1", validRoutes.get("/file1").name);
        assertEquals(Paths.get("/file1"), validRoutes.get("/file1").relativePath);
        assertEquals(Paths.get("/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures/file1"),
                validRoutes.get("/file1").absolutePath);
    }

    @Test
    public void testRootDirectoryRoute() throws IOException {
        Map<String, Route> validRoutes = new Routes(baseDirectory).getValidRoutes();

        assertTrue(validRoutes.containsKey("/"));
        assertEquals(Paths.get("/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures/"),
                validRoutes.get("/").absolutePath);
        assertTrue(validRoutes.get("/").isDirectory);
        assertFalse(validRoutes.get("/").authenticationRequired);
    }

    @Test
    public void testRouteFromConfig() throws IOException {
        Map<String, Route> validRoutes = new Routes(baseDirectory, routeConfig).getValidRoutes();

        assertTrue(validRoutes.containsKey("/form"));
    }

    @Test
    public void testSecureRouteFromConfig() throws IOException {
        Map<String, Route> validRoutes = new Routes(baseDirectory, routeConfig).getValidRoutes();
        String passphrase = Base64.encode("admin:hunter2".getBytes());

        assertTrue(validRoutes.containsKey("/log"));
        assertTrue(validRoutes.get("/log").authenticationRequired);
        assertEquals(validRoutes.get("/log").authenticationString, passphrase);
    }
}