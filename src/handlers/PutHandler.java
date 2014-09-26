package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import routes.Route;
import routes.Routes;

import java.util.Map;

public class PutHandler implements Handler {
    private Request request;
    private ResponseBuilder builder;
    private Map<String, Route> validRoutes;
    private Route requestedRoute;

    public PutHandler(Request clientRequest, Routes routes) {
        this.request = clientRequest;
        this.validRoutes = routes.getValidRoutes();
        this.builder = new ResponseBuilder();
    }

    public Response handle() {
        requestedRoute = validRoutes.get(request.path);
        if (requestedRoute != null) {
            builder.buildOKResponse();
        } else {
            builder.buildNotFoundResponse();
        }
        return builder.getResponse();
    }
}
