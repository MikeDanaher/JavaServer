package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;

public class NotFoundHandler implements Handler {
    private ResponseBuilder builder;

    public NotFoundHandler(Request clientRequest) {
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        builder.buildNotFoundResponse();
        return builder.getResponse();
    }
}
