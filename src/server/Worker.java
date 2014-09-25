package server;

import java.io.IOException;
import java.net.Socket;

public class Worker {
    private Socket client;
    private ServerIO io;
    private String baseDirectory;

    public Worker(Socket client, String directory) {
        this.client = client;
        this.baseDirectory = directory;
        this.io = new ServerIO();
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
        Request request = new Request(requestContent).parseFullRequest();
        Response response = new Response(request, baseDirectory).handleRequest();
        io.writeResponse(response.buildResponse(), client.getOutputStream());
    }

    private boolean isValidRequest(String requestContent) {
        return requestContent.equals("");
    }
}