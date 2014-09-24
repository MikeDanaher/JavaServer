package handlers;

import server.Request;

public class PutHandler implements Handler {
    private Request request;

    public PutHandler(Request clientRequest) {
        this.request = clientRequest;
    }

    public Handler handle() {
        return this;
    }
}
