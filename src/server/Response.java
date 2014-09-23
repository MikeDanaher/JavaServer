package server;

import java.io.File;
import java.io.IOException;
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

    public void handleRequest() throws IOException {
        String method = request.getMethod();
        System.out.println("Method: " + method);
        switch (method) {
            case "GET":
                handleGetRequest();
                break;
            default:
                handleInvalidRequest();
        }
    }

    public byte[] buildResponse() throws IOException {
        byte[] combined = new byte[statusLine.length + headers.length + body.length];

        System.arraycopy(statusLine, 0, combined, 0, statusLine.length);
        System.arraycopy(headers, 0, combined, statusLine.length, headers.length);
        System.arraycopy(body, 0, combined, statusLine.length + headers.length, body.length);

        return combined;
    }

    private byte[] readFileBody(Request request) throws IOException {
        return Files.readAllBytes(Paths.get(baseDirectory + request.getURI()));
    }

    private byte[] get404Body() {
        return "404 Page not found".getBytes();
    }

    private void handleInvalidRequest() {
        statusLine = "HTTP/1.1 405 Method not allowed".getBytes();
        headers = "".getBytes();
        body = "".getBytes();
    }

    private void handleGetRequest() throws IOException {
        headers = ("Content-Type: text/html;charset=UTF-8\r\n\r\n").getBytes();
        File expectedResource = new File(baseDirectory + request.getURI());
        String bodyContent = "";

        if (expectedResource.isDirectory()) {
            for (File fileEntry : expectedResource.listFiles()) {
                bodyContent += buildLink(fileEntry.getName());
            }
            bodyContent = "<html><head><title>Index</title></head><body>" + bodyContent + "</body></html>";
            body = bodyContent.getBytes();
            statusLine = ("HTTP/1.1 200 OK\r\n").getBytes();
        } else if (expectedResource.isFile()) {
            body = readFileBody(request);
            statusLine = ("HTTP/1.1 200 OK\r\n").getBytes();
        } else {
            body = get404Body();
            statusLine = ("HTTP/1.1 404 Not Found\r\n").getBytes();
        }
    }

    private String buildLink(String fileName) {
        String href = "<a href=/";
        String closeHref = ">";
        String closeTag = "</a>";

        return href + fileName + closeHref + fileName + closeTag;
    }
}
