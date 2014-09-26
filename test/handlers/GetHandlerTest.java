package handlers;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import fixtures.TestRoutesConfig;
import org.junit.Test;
import request.Request;
import request.RequestParser;
import response.Response;
import routes.Route;
import routes.Routes;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetHandlerTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private List<Route> routeConfig = TestRoutesConfig.getRoutes(baseDirectory);

    private Response generateResponse(String requestString) throws IOException {
        Request  request  = new RequestParser().parse(requestString);
        Routes   routes   = new Routes(baseDirectory, routeConfig);
        Handler  handler  = new GetHandler(request, routes);
        return handler.handle();
    }

    @Test
    public void testHandleValidDirectoryRequest() throws IOException {
        Response response = generateResponse("GET / HTTP/1.1");
        assertEquals("200", response.statusCode);
    }

    @Test
    public void testHandleValidFileRequest() throws IOException {
        Response response = generateResponse("GET /file1 HTTP/1.1");
        assertEquals("200", response.statusCode);
    }

    @Test
    public void testHandleInvalidFileRequest() throws IOException {
        Response response = generateResponse("GET /foobar HTTP/1.1");
        assertEquals("404", response.statusCode);
    }

    @Test
    public void testHandleUnauthenticatedFileRequest() throws IOException {
        Response response = generateResponse("GET /logs HTTP/1.1");
        assertEquals("401", response.statusCode);
    }

    @Test
    public void testHandleAuthenticatedFileRequest() throws IOException {
        String passphrase = Base64.encode("admin:hunter2".getBytes());
        Response response = generateResponse("GET /logs HTTP/1.1\r\nAuthorization: Basic " + passphrase + "\r\n\r\n");
        assertEquals("200", response.statusCode);
    }
}
