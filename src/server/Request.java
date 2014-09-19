package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Request {
    private BufferedReader reader;

    public Request (InputStream clientInputStream) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(clientInputStream));
    }

    public String getRequest() throws IOException {
        String request = "";
        while (true) {
            String inputLine = reader.readLine();
            request += inputLine + "\r\n";
            if(inputLine.equals("")) {
                break;
            }
        }
        return request;
    }
}
