package request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Request {
    public String                  method;
    public String                  path;
    public String                  version;
    public String                  requestLine;
    public HashMap<String, String> headers = new HashMap<>();
    public HashMap<String, String> body = new HashMap<>();

    public String formatBodyData() {
        StringBuilder content = new StringBuilder();
        Iterator it = body.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            content.append(pairs.getKey());
            content.append(" = ");
            content.append(pairs.getValue());
            content.append("\r\n");
        }
        return content.toString();
    }
}
