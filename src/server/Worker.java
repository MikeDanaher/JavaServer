package server;

import handlers.Handler;
import handlers.HandlerFactory;
import request.Request;
import request.RequestBuilder;
import response.Response;
import routes.Routes;
import routes.RoutesConfig;
import utilities.Logger;

import java.io.IOException;
import java.net.Socket;

public class Worker {
    private Socket client;
    private ServerIO io;
    private String baseDirectory;
    private Routes routes;
    private Request request;

    public Worker(Socket client, String directory) {
        this.client = client;
        this.baseDirectory = directory;
        this.io = new ServerIO();
        this.routes = new Routes(baseDirectory, RoutesConfig.getRoutes(baseDirectory));
    }

    public void handleRequest() {
        try {
            getRequest();
            logRequest();
            respond();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getRequest() throws IOException {
        String requestContent = io.readRequest(client.getInputStream());
        request = new RequestBuilder(requestContent, routes).build();
    }

    private void logRequest() throws IOException {
        Logger.log(request.getRequestLine(), RoutesConfig.logRoute(baseDirectory));
    }

    private void respond() throws IOException {
        Handler handler = new HandlerFactory().build(request);
        Response response = handler.handle();
        io.writeResponse(response.getResponseAsBytes(), client.getOutputStream());
    }
}