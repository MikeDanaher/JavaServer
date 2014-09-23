package server;

import java.io.IOException;
import java.net.Socket;

public class Handler {
    private Socket client;
    private ServerIO io;
    private String baseDirectory;

    public Handler(Socket client, String directory) {
        this.client = client;
        this.baseDirectory = directory;
        this.io = new ServerIO();
    }

    public void handleRequest() {
        try {
            String fullRequest = io.getFullRequest(client.getInputStream());

            if(!fullRequest.equals("")) {
                System.out.println("\n\n" + fullRequest);
                Request request = new Request(fullRequest).parseFullRequest();
                Response response = new Response(request, baseDirectory);
                response.handleRequest();
                io.writeFullResponse(response.buildResponse(), client.getOutputStream());
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}