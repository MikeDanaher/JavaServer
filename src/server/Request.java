package server;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class Request {
    private List<String>            request;
    private String                  requestMethod;
    private String                  requestURI;
    private String                  requestVersion;
    private HashMap<String, String> requestHeaders = new HashMap<>();
    private HashMap<String, String> requestBody = new HashMap<>();

    public Request(String fullRequest) {
        request = Arrays.asList(fullRequest.split("\r\n"));
    }

    public String getMethod() {
        return requestMethod;
    }

    public String getURI() {
        return requestURI;
    }

    public String getVersion() {
        return requestVersion;
    }

    public HashMap<String, String> getHeaders() {
        return requestHeaders;
    }

    public HashMap<String, String> getBody() {
        return requestBody;
    }

    public Request parseFullRequest() {
        int indexOfBlankLine = request.indexOf("");
        parseRequestLine(0);
        parseHeaders(1, indexOfBlankLine);
        if (indexOfBlankLine != -1) {
            parseBody(indexOfBlankLine + 1);
        }
        return this;
    }

    private void parseRequestLine(int firstIndex) {
        String[] requestLine = request.get(firstIndex).split(" ");
        requestMethod = requestLine[0];
        requestURI = requestLine[1];
        requestVersion = requestLine[2];
    }

    private void parseHeaders(int start, int end) {
        for (int i = start; i < end; i++) {
            String[] headerLine = request.get(i).split(" ");
            String key = headerLine[0];
            String value = headerLine[1];
            requestHeaders.put(key, value);
        }
    }

    private void parseBody(int dataIndex) {
        if (isPatch()) {
            parseBodyContent(dataIndex);
        } else {
            parseURLEncodedData(dataIndex);
        }
    }

    private void parseURLEncodedData(int dataIndex) {
        String[] data = request.get(dataIndex).split("&");

        for (String dataPoint : data) {
            String[] params = dataPoint.split("=");
            String key = params[0];
            String value = params[1];
            requestBody.put(key, value);
        }
    }

    private void parseBodyContent(int dataIndex) {
        requestBody.put("Content", request.get(dataIndex));
    }

    private boolean isPatch() {
        return requestMethod.equals("PATCH");
    }
}
