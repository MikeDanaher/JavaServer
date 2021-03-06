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

public class DeleteHandlerTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private List<Route> routeConfig = TestRoutesConfig.getRoutes(baseDirectory);

    private Response generateResponse(String requestContent) throws IOException {
        Routes   routes   = new Routes(baseDirectory, routeConfig);
        Request  request  = new RequestBuilder(requestContent, routes).build();
        Handler  handler  = new HandlerFactory().build(request);
        return handler.handle();
    }

    @Test
    public void testHandleInvalidDeleteRequest() throws IOException {
        Response response = generateResponse("DELETE / HTTP/1.1");
        assertEquals("405", response.statusCode);
    }

    @Test
    public void testHandleValidDeleteRequest() throws IOException {
        Response response = generateResponse("POST /form HTTP/1.1\r\n\r\ndata=test");
        assertEquals("200", response.statusCode);
        Response response2 = generateResponse("DELETE /form HTTP/1.1");
        assertEquals("200", response2.statusCode);
        Response response3 = generateResponse("GET /form HTTP/1.1");
        String bodyContent = new String(response3.body, "UTF-8");
        assertEquals("", bodyContent);

    }

}
