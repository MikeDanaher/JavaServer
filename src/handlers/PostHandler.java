package handlers;

import server.Request;

public class PostHandler implements Handler {
    private Request request;

    public PostHandler(Request clientRequest) {
        this.request = clientRequest;
    }

    public Handler handle() {
        return this;
    }
}
