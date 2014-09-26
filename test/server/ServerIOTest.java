package server;

import mocks.MockClientStreams;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ServerIOTest {

    public String REQUEST  = "GET /public HTTP/1.1\r\nContent-Type: text/plain\r\n\r\n";
    public String RESPONSE = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n";
    public String EMPTY_REQUEST = "";
    public ServerIO io;

    @Before
    public void createIO() {
        io = new ServerIO();
    }

    @Test
    public void testReadValidRequest() throws IOException {
        MockClientStreams mockInput = new MockClientStreams(REQUEST.getBytes());
         assertEquals(REQUEST, io.readRequest(mockInput.getInputStream()));
    }

    @Test
    public void testWriteFullResponse() throws IOException {
        MockClientStreams mockOutput = new MockClientStreams(REQUEST.getBytes());
        io.writeResponse(RESPONSE.getBytes(), mockOutput.getOutputStream());
        assertEquals(mockOutput.getOutputStream().toString(), RESPONSE);
    }

    @Test
    public void testReadEmptyRequest() throws IOException {
        MockClientStreams mockInput = new MockClientStreams(EMPTY_REQUEST.getBytes());
        assertEquals(EMPTY_REQUEST, io.readRequest(mockInput.getInputStream()));
    }
}
