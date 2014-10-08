package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import utilities.FileHandler;
import utilities.IndexGenerator;

import java.io.IOException;
import java.nio.file.Files;

public class GetHandler implements Handler {
    private Request request;
    private ResponseBuilder builder;

    public GetHandler(Request clientRequest) {
        this.request = clientRequest;
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        if (isDirectory()) {
            handleDirectoryRoute();
        } else {
            handleRoute();
        }
        return builder.getResponse();
    }

    private boolean isDirectory() {
        return Files.isDirectory(request.getAbsolutePath());
    }

    private void handleDirectoryRoute() {
        byte[] body = IndexGenerator.generate(request.getAbsolutePath());
        builder.buildResponseBody(body);
        builder.buildOKResponse();
    }

    private void handleRoute() {
        byte[] body;
        try {
            if (isPartialRequest()) {
                body = FileHandler.readPartial(request.getAbsolutePath(), request.getHeaders().get("Range"));
                builder.buildPartialResponse();
            } else {
                body = FileHandler.read(request.getAbsolutePath());
                builder.buildOKResponse();
            }
            builder.buildResponseBody(body);
        } catch (IOException e) {
            body = new byte[0];
            builder.buildOKResponse();
            builder.buildResponseBody(body);
        }
    }

    private boolean isPartialRequest() {
        return request.getHeaders().containsKey("Range");
    }
}