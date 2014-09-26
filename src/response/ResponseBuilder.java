package response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResponseBuilder {
    private Response response;
    private final Map<String, String> STATUS_MESSAGES = createStatusMessages();

    public ResponseBuilder() {
        this.response = new Response();
    }

    public void buildOKResponse() {
        response.statusCode = "200";
    }

    public void buildMethodNotAllowedResponse() {
        response.statusCode = "405";
    }

    public void buildOptionsHeaders() {
        response.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PATCH,PUT");
    }

    public void buildNotFoundResponse() {
        response.statusCode = "404";
        response.body = "404 Page Not Found".getBytes();
    }

    public void buildResponseBody(byte[] body) {
        response.body = body;
    }

    public void buildAuthenticationRequiredResponse() {
        response.statusCode = "401";
        response.setHeader("WWW-Authenticate", "Basic realm=\"because:\"");
        response.body = "Authentication required".getBytes();
    }

    public Response getResponse() {
        buildResponseHead();
        return response;
    }

    private String buildStatusLine() {
        return "HTTP/1.1 " + response.statusCode + " " + STATUS_MESSAGES.get(response.statusCode);
    }

    private void buildResponseHead() {
        String statusLine = buildStatusLine();
        StringBuilder headersString = new StringBuilder();
        Iterator it = response.headers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            headersString.append(pairs.getKey());
            headersString.append(": ");
            headersString.append(pairs.getValue());
            headersString.append("\r\n");
        }
        response.responseHead = (statusLine + "\r\n" + headersString.toString() + "\r\n").getBytes();
    }

    private static Map<String, String> createStatusMessages() {
        Map<String, String> messages = new HashMap<>();
        messages.put("200", "OK");
        messages.put("401", "Unauthorized");
        messages.put("404", "Not Found");
        messages.put("405", "Method Not Allowed");
        return Collections.unmodifiableMap(messages);
    }
}
