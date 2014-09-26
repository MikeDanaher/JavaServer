package response;

import java.util.HashMap;

public class Response {
    public final String            version = "HTTP/1.1";
    public String                  statusCode;
    public HashMap<String, String> headers = new HashMap<>();
    public byte[]                  body = new byte[0];
    public byte[] responseHead;

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public byte[] getResponseAsBytes() {
        byte[] combined = new byte[responseHead.length + body.length];
        System.arraycopy(responseHead, 0, combined, 0, responseHead.length);
        System.arraycopy(body, 0, combined, responseHead.length, body.length);

        return combined;
    }
}
