package handlers;

import request.Request;
import response.Response;
import response.ResponseBuilder;
import routes.Route;
import routes.Routes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
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
            handleRequestedRoute();
        } else {
            builder.buildNotFoundResponse();
        }
        return builder.getResponse();
    }

    private void handleRequestedRoute() {
        if(requestedRoute.isReadOnly) {
            builder.buildMethodNotAllowedResponse();
        } else {
            try {
                String content = buildPutContent();
                writeFile(content);
                builder.buildOKResponse();
            } catch (IOException e) {
                builder.buildMethodNotAllowedResponse();
            }
        }
    }

    private String buildPutContent() {
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

    private void writeFile(String content) throws IOException {
        File file = new File(requestedRoute.absolutePath.toString());
        file.createNewFile();
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.print(content);
        writer.close();
    }
}
