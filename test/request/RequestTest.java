package request;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;


public class RequestTest {

    private Request POSTRequest;
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
        POSTRequest = new RequestParser().parse(POST_URLENCODED);
    }

    @Test
    public void testGetMethod() {
        assertEquals(POSTRequest.method, "POST");
    }

    @Test
    public void testGetURI() {
        assertEquals(POSTRequest.path, "/test");
    }

    @Test
    public void testGetVersion() {
        assertEquals(POSTRequest.version, "HTTP/1.1");
    }

    @Test
    public void testGetHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:5000");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        assertEquals(POSTRequest.headers, headers);
    }

    @Test
    public void testGetPostData() {
        HashMap<String, String> body = new HashMap<>();
        body.put("name", "Test");
        body.put("email", "test@example.com");
        assertEquals(POSTRequest.body, body);
    }

    @Test
    public void testEmptyBody() {
        Request GETRequest = new RequestParser().parse(GET_REQUEST);
        HashMap<String, String> body = new HashMap<>();
        assertEquals(GETRequest.body, body);
    }

    @Test
    public void testGetPatchContent() {
        Request PATCHRequest = new RequestParser().parse(PATCH_REQUEST);
        HashMap<String, String> body = new HashMap<>();
        body.put("Content", "This is some new content");
        assertEquals(PATCHRequest.body, body);
    }
}
