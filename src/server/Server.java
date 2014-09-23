package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private String directory;
    private ServerSocket serverSocket;

    public Server(int port, String directory) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.directory = directory;
    }

    public void start() throws IOException {
        while(!serverSocket.isClosed()) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("Client Connected: " + client);
                Handler handler = new Handler(client, directory);
                handler.handleRequest();
                System.out.println("\nClient Closed");
            } catch (IOException e) {
                System.err.print(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
