package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private String directory;
    private Socket client;
    private ServerSocket serverSocket;

    public Server(int port, String directory) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.directory = directory;
    }

    public void start() throws IOException {
        while(!serverSocket.isClosed()) {
            try {
                client = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
                respond(reader, writer);
            } catch (IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());
            }
        }
    }

    private void respond(BufferedReader reader, BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.1 200\r\n");
        writer.write("Content-Type: text/plain\r\n");
        writer.write("Connection: close\r\n");
        writer.write("\r\n");

        String inputLine;

        while (!client.isClosed()) {
            inputLine = reader.readLine();
            writer.write(inputLine + "\r\n");

            if (inputLine.length() == 0) {
                writer.close();
                reader.close();
                client.close();
            }
        }
    }
}
