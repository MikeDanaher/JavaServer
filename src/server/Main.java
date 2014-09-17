package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {

        int portNumber = 5000;

        System.out.println("I'm a server!");

        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-p")) {
                portNumber = Integer.parseInt(args[i + 1]);
                System.out.println("Listening on port: " + args[i + 1]);
            } else if (args[i].equals("-d")) {
                System.out.println("Default directory is: " + args[i + 1]);
            } else {
                System.out.println("Unrecognized argument: " + args[i] + " " + args[i + 1]);
            }
        }

        ServerSocket listener = new ServerSocket(portNumber);
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
}