package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;

public class RedirectHandler implements Handler {
    private ResponseBuilder builder;
    private Request request;

    public RedirectHandler(Request clientRequest) {
        this.request = clientRequest;
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        String host = request.getHeaders().get("Host");
        builder.buildRedirectResponse(host, request.getRedirectPath());
        return builder.getResponse();
    }
}
