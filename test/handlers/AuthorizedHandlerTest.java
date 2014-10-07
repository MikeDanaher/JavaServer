package handlers;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
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

public class AuthorizedHandlerTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private List<Route> routeConfig = TestRoutesConfig.getRoutes(baseDirectory);

    private Response generateResponse(String requestContent) throws IOException {
        Routes   routes   = new Routes(baseDirectory, routeConfig);
        Request  request  = new RequestBuilder(requestContent, routes).build();
        Handler  handler  = new HandlerFactory().build(request);
        return handler.handle();
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
