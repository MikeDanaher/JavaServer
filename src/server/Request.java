package server;

public class Request {
    private String fullRequest;
    private String requestMethod;
    private String requestURI;
    private String requestVersion;

    public Request(String fullRequest) {
        this.fullRequest = fullRequest;
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

    public Request parseFullRequest() {
        String[] requestLines = fullRequest.split("\r\n");
        parseRequestLine(requestLines[0]);
        return this;
    }

    private void parseRequestLine(String requestLine) {
        String[] rl = requestLine.split(" ");
        requestMethod = rl[0];
        requestURI = rl[1];
        requestVersion = rl[2];
    }
}
