package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;

public class OptionsHandler implements Handler {
    private ResponseBuilder builder;

    public OptionsHandler(Request clientRequest) {
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        builder.buildOKResponse();
        builder.buildOptionsHeaders();
        return builder.getResponse();
    }
}
