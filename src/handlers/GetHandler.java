package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import routes.Route;
import routes.Routes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
        byte[] body = generateIndex();
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

    private byte[] generateIndex() {
        StringBuilder body = new StringBuilder();
        File directory = new File(requestedRoute.absolutePath.toString());
        File[] files = directory.listFiles();

        if (files.length != 0) {
            for (File file : files) {
                if (isValidFile(file)) {
                    body.append(createHTMLLink(file.getName()));
                }
            }
        }

        return body.toString().getBytes();
    }

    private boolean isValidFile(File file) {
        return !file.getName().startsWith(".");
    }

    private String createHTMLLink(String fileName) {
        String href = "<p><a href=/";
        String closeHref = ">";
        String closeTag = "</a></p>";
        return href + fileName + closeHref + fileName + closeTag;
    }

    private byte[] readFile(Path file) {
        byte[] contents = new byte[0];
        try {
            contents = Files.readAllBytes(file);
        } catch (IOException e) {
            contents = "".getBytes();
        }
        return contents;
    }
}