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

public class RedirectHandlerTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private List<Route> routeConfig = TestRoutesConfig.getRoutes(baseDirectory);

    private Response generateResponse(String requestContent) throws IOException {
        Routes   routes   = new Routes(baseDirectory, routeConfig);
        Request  request  = new RequestBuilder(requestContent, routes).build();
        Handler  handler  = new HandlerFactory().build(request);
        return handler.handle();
    }

    @Test
    public void testHandleValidRedirectRequest() throws IOException {
        Response response = generateResponse("GET /redirect HTTP/1.1\r\nHost: localhost:5000");
        assertEquals("307", response.statusCode);
        assertEquals("HTTP://localhost:5000/", response.headers.get("Location"));
    }
}
