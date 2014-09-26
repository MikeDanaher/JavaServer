package handlers;

import request.Request;
import routes.Routes;

public class HandlerFactory {
    private Handler handler;
    private Request request;
    private Routes routes;

    public HandlerFactory(Request clientRequest, Routes routes) {
        this.request = clientRequest;
        this.routes = routes;
    }

    public Handler build() {
        String method = request.method;

        switch (method) {
            case "DELETE":
                handler = new DeleteHandler(request, routes);
                break;
            case "GET":
                handler = new GetHandler(request, routes);
                break;
            case "HEAD":
                handler = new HeadHandler(request, routes);
                break;
            case "OPTIONS":
                handler = new OptionsHandler(request, routes);
                break;
            case "PATCH":
                handler = new PatchHandler(request, routes);
                break;
            case "POST":
                handler = new PostHandler(request, routes);
                break;
            case "PUT":
                handler = new PutHandler(request, routes);
                break;
            default:
                handler = new MethodNotAllowedHandler(request, routes);
        }
        return handler;
    }
}
