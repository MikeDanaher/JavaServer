package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import routes.Route;
import routes.Routes;
import utilities.FileHandler;
import utilities.IndexGenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class GetHandler implements Handler {
    private Request request;
    private ResponseBuilder builder;
    private Map<String, Route> validRoutes;
    private Route requestedRoute;

    public GetHandler(Request clientRequest, Routes routes) {
        this.request = clientRequest;
        this.validRoutes = routes.getValidRoutes();
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        requestedRoute = validRoutes.get(request.path);
        if (requestedRoute != null) {
            handleRequestedRoute();
        } else {
            builder.buildNotFoundResponse();
        }
        return builder.getResponse();
    }

    public void handleRequestedRoute() {
        if (requestedRoute.authenticationRequired) {
            handleAuthenticatedRoute();
        } else if (requestedRoute.isDirectory) {
            handleDirectoryRoute();
        } else {
            handleRoute();
        }
    }

    public void handleDirectoryRoute() {
        byte[] body = IndexGenerator.generate(requestedRoute.absolutePath);
        builder.buildResponseBody(body);
        builder.buildOKResponse();
    }

    public void handleAuthenticatedRoute() {
        String authHeader = request.headers.get("Authorization");
        if (authHeader != null) {
            String passPhrase = authHeader.split(" ")[1];
            if (passPhrase.equals(requestedRoute.authenticationString)) {
                byte[] body = readFile(requestedRoute.absolutePath);
                builder.buildResponseBody(body);
                builder.buildOKResponse();
            } else {
                builder.buildAuthenticationRequiredResponse();
            }
        } else {
            builder.buildAuthenticationRequiredResponse();
        }
    }

    public void handleRoute() {
        byte[] body = readFile(requestedRoute.absolutePath);
        builder.buildResponseBody(body);
        builder.buildOKResponse();
    }

    private byte[] readFile(Path file) {
        byte[] contents = new byte[0];
        try {
            contents = FileHandler.read(file);
        } catch (IOException e) {
            contents = "".getBytes();
        }
        return contents;
    }
}