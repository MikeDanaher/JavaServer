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
                Handler handler = new Handler(client, directory);
                handler.handleRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
