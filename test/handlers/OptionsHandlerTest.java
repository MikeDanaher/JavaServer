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

public class OptionsHandlerTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private List<Route> routeConfig = TestRoutesConfig.getRoutes(baseDirectory);

    private Response generateResponse(String requestContent) throws IOException {
        Routes   routes   = new Routes(baseDirectory, routeConfig);
        Request  request  = new RequestBuilder(requestContent, routes).build();
        Handler  handler  = new HandlerFactory().build(request);
        return handler.handle();
    }

    @Test
    public void testHandleValidOptionsRequest() throws IOException {
        Response response = generateResponse("OPTIONS /method_options HTTP/1.1");
        assertEquals("200", response.statusCode);
        assertEquals("GET,HEAD,POST,OPTIONS,PATCH,PUT", response.headers.get("Allow"));
    }
}
