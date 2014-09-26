package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import routes.Routes;

public class OptionsHandler implements Handler {
    private ResponseBuilder builder;

    public OptionsHandler(Request clientRequest, Routes routes) {
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        builder.buildOKResponse();
        builder.buildOptionsHeaders();
        return builder.getResponse();
    }
}
