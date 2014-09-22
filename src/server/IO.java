package server;

import java.io.*;

public class IO {

    public String getFullRequest(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String fullRequest = "";

        do {
            fullRequest += (char) reader.read();
        } while (reader.ready());

        return fullRequest;
    }

    public void writeFullResponse(byte[] response, OutputStream output) throws IOException {
        DataOutputStream writer = new DataOutputStream(output);
        writer.write(response, 0, response.length);
        writer.close();
    }
}
