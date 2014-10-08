package response;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ResponseTest {
    private ResponseBuilder builder;
    private Response response;

    @Before
    public void createBuilder() {
        builder = new ResponseBuilder();
    }

    @Test
    public void testOKResponse() {
        builder.buildOKResponse();
        response = builder.getResponse();

        assertEquals(response.statusCode, "200");
    }

    @Test
    public void testMethodNotAllowedResponse() {
        builder.buildMethodNotAllowedResponse();
        response = builder.getResponse();

        assertEquals(response.statusCode, "405");
    }

    @Test
    public void testOptionHeader() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Allow", "GET,HEAD,POST,OPTIONS,PATCH,PUT");
        headers.put("Accept-Ranges", "bytes");
        headers.put("Content-Length", "0");

        builder.buildOptionsHeaders();
        response = builder.getResponse();

        assertEquals(response.headers, headers);
    }

    @Test
    public void testNotFoundResponse() {
        builder.buildNotFoundResponse();
        response = builder.getResponse();

        assertEquals(response.statusCode, "404");
    }

    @Test
    public void testBuildResponseHead() throws IOException {
        builder.buildOKResponse();
        response = builder.getResponse();
        String responseText = new String(response.responseHead, "UTF-8");
        assertEquals("HTTP/1.1 200 OK\r\nContent-Length: 0\r\nAccept-Ranges: bytes\r\n\r\n", responseText);
    }
}
