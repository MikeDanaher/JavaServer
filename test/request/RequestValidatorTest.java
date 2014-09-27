package request;

import fixtures.TestRoutesConfig;
import org.junit.Test;
import routes.Route;
import routes.Routes;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RequestValidatorTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private List<Route> routeConfig = TestRoutesConfig.getRoutes(baseDirectory);
    private Routes routes = new Routes(baseDirectory, routeConfig);
    private String requestContent;

    @Test
    public void testReturnsEmptyRequest() {
        requestContent = "";
        Request request = new RequestValidator(requestContent, routes).validate();
        assertEquals("Empty", request.type);
    }

    @Test
    public void testReturnsNotFoundRequest() {
        requestContent = "GET /foobar HTTP/1.1";
        Request request = new RequestValidator(requestContent, routes).validate();
        assertEquals("NotFound", request.type);
    }

    @Test
    public void testReturnsAuthorizedRequest() {
        requestContent = "GET /logs HTTP/1.1";
        Request request = new RequestValidator(requestContent, routes).validate();
        assertEquals("Authorized", request.type);
    }

    @Test
    public void testReturnsRedirectRequest() {
        requestContent = "GET /redirect HTTP/1.1";
        Request request = new RequestValidator(requestContent, routes).validate();
        assertEquals("Redirect", request.type);
    }

    @Test
    public void testReturnsValidRequest() {
        requestContent = "GET /file1 HTTP/1.1";
        Request request = new RequestValidator(requestContent, routes).validate();
        assertEquals("Valid", request.type);
    }
}
