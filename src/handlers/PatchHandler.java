package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import routes.Route;
import routes.Routes;

import java.util.Map;

public class PatchHandler implements Handler {
    private Request request;
    private ResponseBuilder builder;
    private Map<String, Route> validRoutes;
    private Route requestedRoute;

    public PatchHandler(Request clientRequest, Routes routes) {
        this.request = clientRequest;
        this.builder = new ResponseBuilder();
        this.validRoutes = routes.getValidRoutes();
    }

    public Response handle() {
        requestedRoute = validRoutes.get(request.path);
        builder.buildOKResponse();
        return builder.getResponse();
    }
}
