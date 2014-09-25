package server;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class Request {
    private List<String>           fullRequest;
    public String                  method;
    public String                  path;
    public String                  version;
    public HashMap<String, String> headers = new HashMap<>();
    public HashMap<String, String> body = new HashMap<>();

    public Request(String fullRequest) {
        this.fullRequest = Arrays.asList(fullRequest.split("\r\n"));
    }

    public Request parseFullRequest() {
        int indexOfBlankLine = fullRequest.indexOf("");
        parseRequestLine(0);
        parseHeaders(1, indexOfBlankLine);
        if (indexOfBlankLine != -1) {
            parseBody(indexOfBlankLine + 1);
        }
        return this;
    }

    private void parseRequestLine(int firstIndex) {
        String[] requestLine = fullRequest.get(firstIndex).split(" ");
        method = requestLine[0];
        path = requestLine[1];
        version = requestLine[2];
    }

    private void parseHeaders(int start, int end) {
        for (int i = start; i < end; i++) {
            String[] headerLine = fullRequest.get(i).split(" ");
            String key = headerLine[0];
            String value = headerLine[1];
            headers.put(key, value);
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
        String[] data = fullRequest.get(dataIndex).split("&");

        for (String dataPoint : data) {
            String[] params = dataPoint.split("=");
            String key = params[0];
            String value = params[1];
            body.put(key, value);
        }
    }

    private void parseBodyContent(int dataIndex) {
        body.put("Content", fullRequest.get(dataIndex));
    }

    private boolean isPatch() {
        return method.equals("PATCH");
    }
}
