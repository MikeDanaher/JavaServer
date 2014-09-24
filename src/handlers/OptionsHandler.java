package handlers;

import server.Request;

public class OptionsHandler implements Handler {
    private Request request;

    public OptionsHandler(Request clientRequest) {
        this.request = clientRequest;
    }

    public Handler handle() {
        return this;
    }
}
