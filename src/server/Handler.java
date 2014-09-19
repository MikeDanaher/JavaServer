package server;

import java.io.IOException;
import java.net.Socket;

public class Handler {
    public Socket client;
    public String baseDirectory;

    public Handler(Socket client, String directory) {
        this.client = client;
        this.baseDirectory = directory;
    }

    public void handleRequest() {
        try {
            String request = new Request(client.getInputStream()).getRequest();
            Response response = new Response(client.getOutputStream(), request);
            response.write();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
