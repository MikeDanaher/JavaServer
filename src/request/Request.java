package request;

import java.util.HashMap;

public class Request {
    public String                  method;
    public String                  path;
    public String                  version;
    public HashMap<String, String> headers = new HashMap<>();
    public HashMap<String, String> body = new HashMap<>();
}
