package server;

import java.io.IOException;
import java.net.Socket;

public class Handler {
    private Socket client;
    private IO io;
    private String baseDirectory;

    public Handler(Socket client, String directory) {
        this.client = client;
        this.baseDirectory = directory;
        this.io = new IO();
    }

    public void handleRequest() {
        try {
            String fullRequest = io.getFullRequest(client.getInputStream());
            Request request = new Request(fullRequest).parseFullRequest();
            Response response = new Response(request, baseDirectory);
            io.writeFullResponse(response.buildResponse(), client.getOutputStream());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}