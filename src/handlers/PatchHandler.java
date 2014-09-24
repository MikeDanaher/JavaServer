package handlers;

import server.Request;

public class PatchHandler implements Handler {
    private Request request;

    public PatchHandler(Request clientRequest) {
        this.request = clientRequest;
    }

    public Handler handle() {
        return this;
    }
}
