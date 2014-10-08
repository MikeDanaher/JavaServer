package response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResponseBuilder {
    private Response response;
    private final Map<String, String> STATUS_MESSAGES = createStatusMessages();
    private final String NEWLINE = "\r\n";

    public ResponseBuilder() {
        this.response = new Response();
    }

    public void buildOKResponse() {
        response.statusCode = "200";
    }

    public void buildOptionsHeaders() {
        response.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PATCH,PUT");
    }

    public void buildMethodNotAllowedResponse() {
        response.statusCode = "405";
    }

    public void buildNotFoundResponse() {
        response.statusCode = "404";
        response.body = "404 Page Not Found".getBytes();
    }

    public void buildPartialResponse() {
        response.statusCode = "206";
    }

    public void buildAuthenticationRequiredResponse() {
        response.statusCode = "401";
        response.setHeader("WWW-Authenticate", "Basic realm=\"because:\"");
        response.body = "Authentication required".getBytes();
    }

    public void buildRedirectResponse(String host, String redirectPath) {
        response.statusCode = "307";
        response.setHeader("Location", "HTTP://" + host + redirectPath);
    }

    public void buildResponseBody(byte[] body) {
        response.body = body;
    }

    public Response getResponse() {
        buildResponseHead();
        return response;
    }

    private void buildResponseHead() {
        setDefaultHeaders();
        String statusLine = buildStatusLine();
        String headersString = buildHeaders();
        response.responseHead = (statusLine + NEWLINE + headersString + NEWLINE).getBytes();
    }

    private String buildStatusLine() {
        return response.version + " " + response.statusCode + " " + STATUS_MESSAGES.get(response.statusCode);
    }

    private void setDefaultHeaders() {
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", getContentLength());
    }

    private String getContentLength() {
        return Integer.toString(response.body.length);
    }

    private String buildHeaders() {
        StringBuilder headersString = new StringBuilder();
        Iterator it = response.headers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            headersString.append(pairs.getKey());
            headersString.append(": ");
            headersString.append(pairs.getValue());
            headersString.append(NEWLINE);
        }
        return headersString.toString();
    }

    private static Map<String, String> createStatusMessages() {
        Map<String, String> messages = new HashMap<>();
        messages.put("200", "OK");
        messages.put("206", "Partial Content");
        messages.put("401", "Unauthorized");
        messages.put("404", "Not Found");
        messages.put("405", "Method Not Allowed");
        return Collections.unmodifiableMap(messages);
    }
}
