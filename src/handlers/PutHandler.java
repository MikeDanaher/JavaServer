package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import utilities.FileHandler;

import java.io.IOException;

public class PutHandler implements Handler {
    private Request request;
    private ResponseBuilder builder;

    public PutHandler(Request clientRequest) {
        this.request = clientRequest;
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        if(request.getIsReadOnly()) {
            builder.buildMethodNotAllowedResponse();
        } else {
            try {
                String content = request.getBody();
                FileHandler.writeOver(request.getAbsolutePath(), content.getBytes());
                builder.buildOKResponse();
            } catch (IOException e) {
                builder.buildMethodNotAllowedResponse();
            }
        }
        return builder.getResponse();
    }
}
