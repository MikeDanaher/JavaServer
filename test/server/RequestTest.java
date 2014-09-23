package server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTest {

    private String FULL_GET_REQUEST = "GET /test HTTP/1.1";
    private Request request;

    @Before
    public void createRequest() {
        request = new Request(FULL_GET_REQUEST).parseFullRequest();
    }

    @Test
    public void testGetMethod() {
        assertEquals(request.getMethod(), "GET");
    }

    @Test
    public void testGetURI() {
        assertEquals(request.getURI(), "/test");
    }

    @Test
    public void testGetVersion() {
        assertEquals(request.getVersion(), "HTTP/1.1");
    }

}
