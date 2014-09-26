package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import routes.Routes;

public class MethodNotAllowedHandler implements Handler {
    private ResponseBuilder builder;

    public MethodNotAllowedHandler(Request clientRequest, Routes routes) {
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        builder.buildMethodNotAllowedResponse();
        return builder.getResponse();
    }
}
