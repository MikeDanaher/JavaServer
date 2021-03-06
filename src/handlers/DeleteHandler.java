package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import utilities.FileHandler;

import java.io.IOException;

public class DeleteHandler implements Handler {
    private Request request;
    private ResponseBuilder builder;

    public DeleteHandler (Request clientRequest) {
        this.request = clientRequest;
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        if (request.getIsReadOnly()) {
            builder.buildMethodNotAllowedResponse();
        } else {
            try {
                FileHandler.delete(request.getAbsolutePath());
                builder.buildOKResponse();
            } catch (IOException e) {
                builder.buildNotFoundResponse();
            }
        }
        return builder.getResponse();
    }
}
