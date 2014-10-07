package server;

import utilities.ArgsParser;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            ArgsParser argsParser = new ArgsParser(args);
            printServerSetup(argsParser.getPort(), argsParser.getDirectory());
            Server server = new Server(argsParser.getPort(), argsParser.getDirectory());
            new Thread(server).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printServerSetup(int port, String directory) {
        System.out.println("Starting server\nConnected on port: "
                + port + "\nDefault directory is: " + directory);
    }
}