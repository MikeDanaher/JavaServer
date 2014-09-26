package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import routes.Route;
import routes.Routes;
import utilities.FileHandler;

import java.io.IOException;
import java.util.Map;

public class DeleteHandler implements Handler {
    private Request request;
    private ResponseBuilder builder;
    private Map<String, Route> validRoutes;
    private Route requestedRoute;

    public DeleteHandler (Request clientRequest, Routes routes) {
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

    private void handleRequestedRoute() {
        if (requestedRoute.isReadOnly) {
            builder.buildMethodNotAllowedResponse();
        } else {
            try {
                FileHandler.delete(requestedRoute.absolutePath);
                builder.buildOKResponse();
            } catch (IOException e) {
                builder.buildNotFoundResponse();
            }
        }
    }
}
