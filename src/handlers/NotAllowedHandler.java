package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;

public class NotAllowedHandler implements Handler {
    private ResponseBuilder builder;

    public NotAllowedHandler(Request clientRequest) {
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        builder.buildMethodNotAllowedResponse();
        return builder.getResponse();
    }
}
