package server;

import java.io.*;

public class ServerIO {

    public String getFullRequest(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder fullRequest = new StringBuilder();

        do {
            fullRequest.append((char)reader.read());
        } while (reader.ready());

        return fullRequest.toString();
    }

    public void writeFullResponse(byte[] response, OutputStream output) throws IOException {
        DataOutputStream writer = new DataOutputStream(output);
        writer.write(response, 0, response.length);
        writer.close();
    }
}
