package server;

public class Main {

    public static void main(String[] args) {
        System.out.println("I'm a server!");
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-p")) {
                System.out.println("Listening on port: " + args[i + 1]);
            } else if (args[i].equals("-d")) {
                System.out.println("Default directory is: " + args[i + 1]);
            } else {
                System.out.println("Unrecognized argument: " + args[i] + " " + args[i + 1]);
            }
        }
    }
}