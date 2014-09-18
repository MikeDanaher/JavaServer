package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {

        ArgsParser argsParser = new ArgsParser(args);
        printServerSetup(argsParser.getPort(), argsParser.getDirectory());

        ServerSocket listener = new ServerSocket(argsParser.getPort());
        for (;;) {
            Socket clientSocket = listener.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.print("HTTP/1.1 200 \r\n");
            out.print("Content-Type: text/plain\r\n");
            out.print("Connection: close\r\n");
            out.print("\r\n");

            String inputLine;

            while (!clientSocket.isClosed()) {
                inputLine = in.readLine();
                out.print(inputLine + "\r\n");

                if (inputLine.length() == 0) {
                    out.close();
                    in.close();
                    clientSocket.close();
                }
            }
        }
    }

    private static void printServerSetup(int port, String directory) {
        System.out.println("Starting server\nConnected on port: "
                + port + "\nDefault directory is: " + directory);
    }
}