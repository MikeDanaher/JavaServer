package response;

import org.junit.Before;
import org.junit.Test;

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

        builder.buildOptionsHeaders();
        response = builder.getResponse();

        assertEquals(response.headers, headers);
    }
}
