package server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Response {
    private Request request;
    private String baseDirectory;
    private byte[] statusLine;
    private byte[] headers;
    private byte[] body;

    public Response (Request request, String baseDirectory) {
        this.request = request;
        this.baseDirectory = baseDirectory;
    }

    public byte[] buildResponse() throws IOException {
        boolean resourceExists = new File(baseDirectory + request.getURI()).isFile();

        if (resourceExists) {
            body = getBody(request);
            statusLine = ("HTTP/1.1 " + getStatusCode(request) + "\r\n").getBytes();
        } else {
            body = get404Body();
            statusLine = ("HTTP/1.1 404 Not Found\r\n").getBytes();
        }

        headers = ("Content-Type: text/plain\r\nConnection: close\r\n\r\n").getBytes();

        byte[] combined = new byte[statusLine.length + headers.length + body.length];

        System.arraycopy(statusLine, 0, combined, 0, statusLine.length);
        System.arraycopy(headers, 0, combined, statusLine.length, headers.length);
        System.arraycopy(body, 0, combined, statusLine.length + headers.length, body.length);

        return combined;
    }

    private String getStatusCode(Request request) {
        String method = request.getMethod();
        switch (method) {
            case "GET":
                return "200 OK";
            case "POST":
                return "200 OK";
            case "PUT":
                return "200 OK";
            default:
                return "404 Not Found";
        }
    }

    private byte[] getBody(Request request) throws IOException {
        return Files.readAllBytes(Paths.get(baseDirectory + request.getURI()));
    }

    private byte[] get404Body() {
        return "404 Page not found".getBytes();
    }
}
