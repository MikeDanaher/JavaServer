package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import utilities.FileHandler;
import utilities.IndexGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        byte[] body = readFile(request.getAbsolutePath());
        builder.buildResponseBody(body);
        builder.buildOKResponse();
    }

    private byte[] readFile(Path file) {
        byte[] contents;
        try {
            contents = FileHandler.read(file);
        } catch (IOException e) {
            contents = "".getBytes();
        }
        return contents;
    }
}