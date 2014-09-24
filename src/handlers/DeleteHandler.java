package handlers;

import server.Request;

public class DeleteHandler implements Handler {
    private Request request;

    public DeleteHandler (Request clientRequest) {
        this.request = clientRequest;
    }

    public Handler handle() {
        return this;
    }
}
