package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Response {
    public BufferedWriter writer;
    public String request;

    public Response (OutputStream clientOutputStream, String request) {
        this.writer = new BufferedWriter(new OutputStreamWriter(clientOutputStream));
        this.request = request;
    }

    public String buildResponse() {
        String responseHeader = "HTTP/1.1 200\r\nContent-Type: text/plain\r\nConnection: close\r\n\r\n";
        return responseHeader + request;
    }

    public void write() {
        try {
            writer.write(buildResponse());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
