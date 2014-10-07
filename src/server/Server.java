package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {
    private String directory;
    private ServerSocket serverSocket;
    private ExecutorService executor;

    public Server(int port, String directory) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.directory = directory;
        this.executor = Executors.newFixedThreadPool(8);
    }

    public void run() {
        while(!serverSocket.isClosed()) {
            try {
                Socket client = serverSocket.accept();
                executor.execute(new Worker(client, directory));
            } catch (IOException e) {
                System.err.print(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
