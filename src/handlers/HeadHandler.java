package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;

public class HeadHandler implements Handler {
    private Request request;
    private ResponseBuilder builder;

    public HeadHandler (Request clientRequest) {
        this.request = clientRequest;
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        builder.buildOKResponse();
        return builder.getResponse();
    }
}
