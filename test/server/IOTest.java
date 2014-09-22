package server;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class IOTest {

    public String REQUEST  = "GET /public HTTP/1.1\r\nContent-Type: text/plain\r\n\r\n";
    public String RESPONSE = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n";
    public IO io;

    @Before
    public void createIO() {
        io = new IO();
    }

    @Test
    public void testReadFullRequest() throws IOException {
        MockClientStreams mockInput = new MockClientStreams(REQUEST.getBytes());
        assertEquals(REQUEST, io.getFullRequest(mockInput.getInputStream()));
    }

    @Test
    public void testWriteFullResponse() throws IOException {
        MockClientStreams mockOutput = new MockClientStreams(REQUEST.getBytes());
        io.writeFullResponse(RESPONSE.getBytes(), mockOutput.getOutputStream());

        assertEquals(mockOutput.getOutputStream().toString(), RESPONSE);

    }
}
