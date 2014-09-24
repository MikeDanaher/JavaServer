package handlers;

import server.Request;

public class MethodNotAllowedHandler implements Handler {
    private Request request;

    public MethodNotAllowedHandler(Request clientRequest) {
        this.request = clientRequest;
    }

    public Handler handle() {
        return this;
    }
}
