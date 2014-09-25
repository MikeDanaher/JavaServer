package handlers;

import server.Request;

public class HandlerFactory {
    Handler handler;

    public Handler build(Request request) {
        String method = request.method;

        switch (method) {
            case "DELETE":
                handler = new DeleteHandler(request);
                break;
            case "GET":
                handler = new GetHandler(request);
                break;
            case "HEAD":
                handler = new HeadHandler(request);
                break;
            case "OPTIONS":
                handler = new OptionsHandler(request);
                break;
            case "PATCH":
                handler = new PatchHandler(request);
                break;
            case "POST":
                handler = new PostHandler(request);
                break;
            case "PUT":
                handler = new PutHandler(request);
                break;
            default:
                handler = new MethodNotAllowedHandler(request);
        }
        return handler;
    }
}
