package handlers;

import server.Request;

public class GetHandler implements Handler {
    private Request request;

    public GetHandler(Request clientRequest) {
        request = clientRequest;
    }

    public Handler handle() {
        return this;
    }
}
