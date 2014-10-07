package handlers;

import fixtures.TestRoutesConfig;
import org.junit.Test;
import request.Request;
import request.RequestBuilder;
import response.Response;
import routes.Route;
import routes.Routes;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetHandlerTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private List<Route> routeConfig = TestRoutesConfig.getRoutes(baseDirectory);

    private Response generateResponse(String requestContent) throws IOException {
        Routes   routes   = new Routes(baseDirectory, routeConfig);
        Request  request  = new RequestBuilder(requestContent, routes).build();
        Handler  handler  = new HandlerFactory().build(request);
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

}
