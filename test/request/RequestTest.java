package request;

import fixtures.TestRoutesConfig;
import org.junit.Before;
import org.junit.Test;
import routes.Route;
import routes.Routes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class RequestTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures";
    private List<Route> routeConfig = TestRoutesConfig.getRoutes(baseDirectory);
    private Routes routes = new Routes(baseDirectory, routeConfig);
    private Request PostRequest;

    private String POST_URLENCODED = "POST /form HTTP/1.1\r\n" +
                                     "Host: localhost:5000\r\n" +
                                     "Connection: keep-alive\r\n" +
                                     "Content-Type: application/x-www-form-urlencoded\r\n" +
                                     "\r\n" +
                                     "name=Test&email=test@example.com";

    private String PATCH_REQUEST = "PATCH /form HTTP/1.1\r\n" +
                                   "Host: localhost:5000\r\n" +
                                   "Connection: keep-alive\r\n" +
                                   "Content-Type: application/x-www-form-urlencoded\r\n" +
                                   "\r\n" +
                                   "This is some new content";

    private String GET_REQUEST = "GET /logs HTTP/1.1\r\n" +
                                 "Host: localhost:5000\r\n" +
                                 "Connection: keep-alive\r\n" +
                                 "Content-Type: text/html\r\n" +
                                 "\r\n";

    @Before
    public void createRequest() {
        PostRequest = new RequestBuilder(POST_URLENCODED, routes).build();
    }

    @Test
    public void testGetType() {
        assertEquals("Post", PostRequest.getType());
    }

    @Test
    public void testGetMethod() {
        assertEquals("POST", PostRequest.getMethod());
    }

    @Test
    public void testGetPath() {
        assertEquals("/form", PostRequest.getPath());
    }

    @Test
    public void testGetVersion() {
        assertEquals("HTTP/1.1", PostRequest.getVersion());
    }

    @Test
    public void testGetRequestLine() {
        assertEquals("POST /form HTTP/1.1", PostRequest.getRequestLine());
    }

    @Test
    public void testGetAbsolutePath() {
        Path absolutePath = Paths.get(baseDirectory + "/form");
        assertEquals(absolutePath, PostRequest.getAbsolutePath());
    }

    @Test
    public void testGetPassPhrase() {
        Request GetRequest = new RequestBuilder(GET_REQUEST, routes).build();
        assertEquals("YWRtaW46aHVudGVyMg==", GetRequest.getPassPhrase());
    }

    @Test
    public void testGetRedirectPath() {
        String REDIRECT_REQUEST = "GET /redirect HTTP/1.1";
        Request GetRequest = new RequestBuilder(REDIRECT_REQUEST, routes).build();
        assertEquals("/", GetRequest.getRedirectPath());
    }

    @Test
    public void testGetReadOnly() {
        String REDIRECT_REQUEST = "GET /redirect HTTP/1.1";
        Request GetRequest = new RequestBuilder(REDIRECT_REQUEST, routes).build();
        assertTrue(GetRequest.getIsReadOnly());
    }

    @Test
    public void testGetHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:5000");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        assertEquals(headers, PostRequest.getHeaders());
    }

    @Test
    public void testGetPostFormattedData() {
        String body = "email = test@example.com\r\nname = Test\r\n";
        assertEquals(body, PostRequest.getBody());
    }

    @Test
    public void testEmptyBody() {
        Request GetRequest = new RequestBuilder(GET_REQUEST, routes).build();
        assertEquals("", GetRequest.getBody());
    }

    @Test
    public void testGetPatchContent() {
        Request PatchRequest = new RequestBuilder(PATCH_REQUEST, routes).build();
        String body = "Content = This is some new content\r\n";
        assertEquals(body, PatchRequest.getBody());
    }
}
