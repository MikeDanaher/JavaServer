package server;

import utilities.ArgsParser;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ArgsParser argsParser = new ArgsParser(args);
        printServerSetup(argsParser.getPort(), argsParser.getDirectory());
        Server server = new Server(argsParser.getPort(), argsParser.getDirectory());
        server.start();
    }

    private static void printServerSetup(int port, String directory) {
        System.out.println("Starting server\nConnected on port: "
                + port + "\nDefault directory is: " + directory);
    }
}