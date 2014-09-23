package server;

import java.io.*;

public class ServerIO {

    public String getFullRequest(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder fullRequest = new StringBuilder();
        int nextChar;

        while ((nextChar = reader.read()) != -1) {
            fullRequest.append((char)nextChar);

            if (!reader.ready()) {
                break;
            }
        }

        return fullRequest.toString();
    }

    public void writeFullResponse(byte[] response, OutputStream output) throws IOException {
        DataOutputStream writer = new DataOutputStream(output);
        writer.write(response, 0, response.length);
        writer.flush();
        writer.close();
    }
}
