package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private String directory;
    private ServerSocket serverSocket;
    public  Socket client;

    public Server(int port, String directory) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.directory = directory;
    }

    public void start() throws IOException {
        while(!serverSocket.isClosed()) {
            try {
                client = serverSocket.accept();
                String request = new Request(client.getInputStream()).getRequest();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
                respond(request, writer);
            } catch (IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());
            }
        }
    }

    private void respond(String request, BufferedWriter writer) throws IOException {
        String responseHeader = "HTTP/1.1 200\r\nContent-Type: text/plain\r\nConnection: close\r\n\r\n";
        writer.write(responseHeader + request);
        writer.close();
        client.close();
    }
}
