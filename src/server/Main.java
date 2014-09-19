package server;

import java.io.IOException;

public class Main {
    public static Server     server;
    public static ArgsParser argsParser;

    public static void main(String[] args) throws IOException {
        argsParser = new ArgsParser(args);
        printServerSetup(argsParser.getPort(), argsParser.getDirectory());

        server = new Server(argsParser.getPort(), argsParser.getDirectory());
        server.start();
    }

    private static void printServerSetup(int port, String directory) {
        System.out.println("Starting server\nConnected on port: "
                + port + "\nDefault directory is: " + directory);
    }
}