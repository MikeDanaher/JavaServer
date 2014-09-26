package request;

import java.util.Arrays;
import java.util.List;

public class RequestParser {
    private Request request;
    private List<String> requestList;

    public Request parse(String requestContent) {
        request = new Request();
        requestList = Arrays.asList(requestContent.split("\r\n"));
        int indexOfBlankLine = requestList.indexOf("");

        parseRequestLine(0);
        parseHeaders(1);
        if (requestHasData()) {
            parseBody(indexOfBlankLine + 1);
        }

        return request;
    }

    private void parseRequestLine(int firstIndex) {
        String[] requestLine = requestList.get(firstIndex).split(" ");
        request.method = requestLine[0];
        request.path = requestLine[1];
        request.version = requestLine[2];
    }

    private void parseHeaders(int start) {
        for (int i = start; i < requestList.size(); i++) {
            if (requestList.get(i).equals("")) {
                break;
            }
            String[] headerLine = requestList.get(i).split(": ");
            String key = headerLine[0];
            String value = headerLine[1];
            request.headers.put(key, value);
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
        String[] data = requestList.get(dataIndex).split("&");

        for (String dataPoint : data) {
            String[] params = dataPoint.split("=");
            String key = params[0];
            String value = params[1];
            request.body.put(key, value);
        }
    }

    private void parseBodyContent(int dataIndex) {
        request.body.put("Content", requestList.get(dataIndex));
    }

    private boolean requestHasData() {
        return requestList.contains("");
    }

    private boolean isPatch() {
        return request.method.equals("PATCH");
    }
}
