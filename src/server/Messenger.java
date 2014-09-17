package server;

public class Messenger {

    private String message;

    public Messenger (String message) {
        this.message = message;
    }

    public String printMessage() {
        System.out.println(message);
        return message;
    }
}
