package handlers;

import server.Request;

public class HeadHandler implements Handler {
    private Request request;

    public HeadHandler(Request clientRequest) {
        this.request = clientRequest;
    }

    public Handler handle() {
        return this;
    }
}
