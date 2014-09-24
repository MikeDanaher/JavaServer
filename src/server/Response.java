package server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Response {
    private Request request;
    private String baseDirectory;
    private String statusCode;
    private HashMap<String, String> headers = new HashMap<>();
    private byte[] responseHead;
    private byte[] responseBody;
    private final Map<String, String> STATUS_MESSAGES = createStatusMessages();

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
            case "POST":
                handlePostRequest();
                break;
            case "PUT":
                handlePostRequest();
                break;
            case "PATCH":
                handlePatchRequest();
                break;
            case "OPTIONS":
                handleOptionsRequest();
                break;
            case "HEAD":
                handleGetRequest();
                break;
            case "DELETE":
                handleDeleteRequest();
                break;
            default:
                handleInvalidRequest();
        }
        buildStatusLine();
    }

    private String buildStatusLine() {
        return request.getVersion() + " " + statusCode + " " + STATUS_MESSAGES.get(statusCode);
    }

    public byte[] buildResponse() throws IOException {
        buildResponseHead();

        byte[] combined = new byte[responseHead.length + responseBody.length];
        System.arraycopy(responseHead, 0, combined, 0, responseHead.length);
        System.arraycopy(responseBody, 0, combined, responseHead.length, responseBody.length);

        return combined;
    }

    private void buildResponseHead() {
        String statusLine = buildStatusLine();
        String headersString = "";
        Iterator it = headers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            headersString += pairs.getKey() + ": " + pairs.getValue() + "\r\n";
            it.remove();
        }
        responseHead = (statusLine + "\r\n" + headersString + "\r\n").getBytes();
    }

    private byte[] readFileBody(Request request) throws IOException {
        return Files.readAllBytes(Paths.get(baseDirectory + request.getURI()));
    }

    private byte[] get404Body() {
        return "404 Page not found".getBytes();
    }

    private void setHeader(String key, String value) {
        headers.put(key, value);
    }

    private void handleInvalidRequest() {
        statusCode = "405";
        responseBody = "".getBytes();
    }

    private void handleGetRequest() throws IOException {
        File expectedResource = new File(baseDirectory + request.getURI());
        String bodyContent = "";

        if (expectedResource.isDirectory()) {
            if (request.getURI().equals("/form")) {
                statusCode = "200";
                responseBody = "".getBytes();
                setHeader("Content-Type", "text/html;charset=UTF-8");
            } else {
                for (File fileEntry : expectedResource.listFiles()) {
                    bodyContent += buildLink(fileEntry.getName());
                }
                bodyContent = "<html><head><title>Index</title></head><body>" + bodyContent + "</body></html>";
                responseBody = bodyContent.getBytes();
                statusCode = "200";
                setHeader("Content-Type", "text/html;charset=UTF-8");
            }
        } else if (expectedResource.isFile()) {
            responseBody = readFileBody(request);
            statusCode = "200";
            setHeader("Content-Type", "text/plain");
        } else {
            responseBody = get404Body();
            statusCode = "404";
            setHeader("Content-Type", "text/plain");
        }
    }

    private String buildLink(String fileName) {
        String href = "<p><a href=/";
        String closeHref = ">";
        String closeTag = "</a></p>";

        return href + fileName + closeHref + fileName + closeTag;
    }

    private void handleOptionsRequest() {
        statusCode = "200";
        setHeader("Allow", "GET,HEAD,POST,OPTIONS,PATCH,PUT");
        setHeader("Content-Type", "text/plain");
        responseBody = "".getBytes();
    }

    private void handlePatchRequest() {
        statusCode = "204";
        setHeader("Content-Type", "text/plain");
        responseBody = request.getBody().toString().getBytes();
    }

    private void handleDeleteRequest() throws IOException {
        Files.delete(Paths.get(baseDirectory + request.getURI()));
        statusCode = "200";
        setHeader("Content-Type", "text/plain");
        responseBody = "".getBytes();
    }

    private void handlePostRequest() throws IOException {

        if (requestIsAllowed(request.getURI())) {
            String content = buildPostContent();
            File file = new File(baseDirectory + request.getURI());
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.print(content);
            writer.close();
            statusCode = "200";
            setHeader("Content-Type", "text/plain");
            responseBody = "".getBytes();
        } else {
            handleInvalidRequest();
        }
    }

    private boolean requestIsAllowed(String path) {
        return path.equals("/form");
    }

    private String buildPostContent() {
        String content = "";
        Iterator it = request.getBody().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            content += pairs.getKey() + " = " + pairs.getValue() + "\r\n";
            it.remove();
        }
        return content;
    }

    private static Map<String, String> createStatusMessages() {
        Map<String, String> messages = new HashMap<>();
        messages.put("200", "OK");
        messages.put("204", "No Content");
        messages.put("404", "Not Found");
        messages.put("405", "Method Not Allowed");
        return Collections.unmodifiableMap(messages);
    }

}
