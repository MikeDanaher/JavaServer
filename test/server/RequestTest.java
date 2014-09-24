package server;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

public class RequestTest {

    private Request POSTRequest;
    private Request GETRequest;
    private Request PATCHRequest;
    private String POST_URLENCODED = "POST /test HTTP/1.1\r\n" +
                                     "Host: localhost:5000\r\n" +
                                     "Connection: keep-alive\r\n" +
                                     "Content-Type: application/x-www-form-urlencoded\r\n" +
                                     "\r\n" +
                                     "name=Test&email=test@example.com";

    private String PATCH_REQUEST = "PATCH /test.txt HTTP/1.1\r\n" +
                                   "Host: localhost:5000\r\n" +
                                   "Connection: keep-alive\r\n" +
                                   "Content-Type: application/x-www-form-urlencoded\r\n" +
                                   "\r\n" +
                                   "This is some new content";

    private String GET_REQUEST = "POST /test HTTP/1.1\r\n" +
                                 "Host: localhost:5000\r\n" +
                                 "Connection: keep-alive\r\n" +
                                 "Content-Type: text/html\r\n" +
                                 "\r\n";

    @Before
    public void createRequest() {
        POSTRequest = new Request(POST_URLENCODED).parseFullRequest();
    }

    @Test
    public void testGetMethod() {
        assertEquals(POSTRequest.getMethod(), "POST");
    }

    @Test
    public void testGetURI() {
        assertEquals(POSTRequest.getURI(), "/test");
    }

    @Test
    public void testGetVersion() {
        assertEquals(POSTRequest.getVersion(), "HTTP/1.1");
    }

    @Test
    public void testGetHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Host:", "localhost:5000");
        headers.put("Connection:", "keep-alive");
        headers.put("Content-Type:", "application/x-www-form-urlencoded");
        assertEquals(POSTRequest.getHeaders(), headers);
    }

    @Test
    public void testGetPostData() {
        HashMap<String, String> body = new HashMap<>();
        body.put("name", "Test");
        body.put("email", "test@example.com");
        assertEquals(POSTRequest.getBody(), body);
    }

    @Test
    public void testEmptyBody() {
        GETRequest = new Request(GET_REQUEST).parseFullRequest();
        HashMap<String, String> body = new HashMap<>();
        assertEquals(GETRequest.getBody(), body);
    }

    @Test
    public void testGetPatchContent() {
        PATCHRequest = new Request(PATCH_REQUEST).parseFullRequest();
        HashMap<String, String> body = new HashMap<>();
        body.put("Content", "This is some new content");
        assertEquals(PATCHRequest.getBody(), body);
    }
}
