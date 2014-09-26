package mocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MockClientStreams {
    private InputStream inputStream;
    private OutputStream outputStream;

    public MockClientStreams(byte[] data) {
        inputStream = new ByteArrayInputStream(data);
        outputStream = new ByteArrayOutputStream();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }
}
