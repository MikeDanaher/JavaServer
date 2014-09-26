package server;

import handlers.Handler;
import handlers.HandlerFactory;
import request.Request;
import request.RequestParser;
import response.Response;
import routes.RoutesConfig;
import routes.Routes;
import utilities.Logger;

import java.io.IOException;
import java.net.Socket;

public class Worker {
    private Socket client;
    private ServerIO io;
    private String baseDirectory;
    private Routes routes;

    public Worker(Socket client, String directory) {
        this.client = client;
        this.baseDirectory = directory;
        this.io = new ServerIO();
        this.routes = new Routes(baseDirectory, RoutesConfig.getRoutes(baseDirectory));
    }

    public void handleRequest() {
        try {
            String requestContent = io.readRequest(client.getInputStream());

            if(isValidRequest(requestContent)) {
                respond(requestContent);
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void respond(String requestContent) throws IOException {
        Request request = new RequestParser().parse(requestContent);

        Logger.log(request.requestLine, RoutesConfig.logRoute(baseDirectory));

        Handler handler = new HandlerFactory(request, routes).build();
        Response response = handler.handle();
        io.writeResponse(response.getResponseAsBytes(), client.getOutputStream());
    }

    private boolean isValidRequest(String requestContent) {
        return !requestContent.equals("");
    }
}