package handlers;

import fixtures.TestRoutesConfig;
import org.junit.After;
import org.junit.Test;
import request.Request;
import request.RequestParser;
import response.Response;
import routes.Route;
import routes.Routes;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PostHandlerTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private List<Route> routeConfig = TestRoutesConfig.getRoutes(baseDirectory);

    private Response generateResponse(String requestString) throws IOException {
        Request  request  = new RequestParser().parse(requestString);
        Routes   routes   = new Routes(baseDirectory, routeConfig);
        Handler  handler  = new HandlerFactory(request, routes).build();
        return handler.handle();
    }

    @After
    public void cleanUp() throws IOException {
        generateResponse("DELETE /form HTTP/1.1");
    }

    @Test
    public void testHandleValidRouteRequest() throws IOException {
        Response response = generateResponse("POST /form HTTP/1.1");
        assertEquals("200", response.statusCode);
    }

    @Test
    public void testCreateNewFile() throws IOException {
        Response response = generateResponse("POST /form HTTP/1.1\r\n\r\ndata=test");
        assertEquals("200", response.statusCode);
        Response response2 = generateResponse("GET /form HTTP/1.1");
        String responseBody = new String(response2.body, "UTF-8");
        assertEquals("data = test\r\n", responseBody);
    }

    @Test
    public void testHandleInvalidRequest() throws IOException {
        Response response = generateResponse("POST /foobar HTTP/1.1");
        assertEquals("404", response.statusCode);
    }

    @Test
    public void testHandleMethodNotAllowedRequest() throws IOException {
        Response response = generateResponse("POST /file1 HTTP/1.1");
        assertEquals("405", response.statusCode);
    }
}
