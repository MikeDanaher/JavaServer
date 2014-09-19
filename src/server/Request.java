package server;

import java.io.*;

public class Request {
    private BufferedReader reader;

    public Request (InputStream clientInputStream) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(clientInputStream));
    }

    public String getRequestLine() throws IOException {
        return reader.readLine();
    }
}
