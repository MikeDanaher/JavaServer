package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import routes.Route;
import routes.Routes;
import utilities.FileHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class PostHandler implements Handler {
    private Request request;
    private ResponseBuilder builder;
    private Map<String, Route> validRoutes;
    private Route requestedRoute;

    public PostHandler(Request clientRequest, Routes routes) {
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
                String content = buildPostContent();
                FileHandler.writeOver(requestedRoute.absolutePath, content.getBytes());
                builder.buildOKResponse();
            } catch (IOException e) {
                builder.buildMethodNotAllowedResponse();
            }
        }
    }

    private String buildPostContent() {
        StringBuilder content = new StringBuilder();
        Iterator it = request.body.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            content.append(pairs.getKey());
            content.append(" = ");
            content.append(pairs.getValue());
            content.append("\r\n");
        }
        return content.toString();
    }
}
