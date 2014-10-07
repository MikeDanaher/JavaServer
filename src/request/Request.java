package request;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Request {
    private String              type;
    private String              method;
    private String              path;
    private String              version;
    private String              requestLine;
    private Path                absolutePath;
    private String              passPhrase = "";
    private String              redirectPath = "";
    private boolean             isReadOnly = false;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> body = new HashMap<>();

    public String getType() {
        return type;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public Path getAbsolutePath() {
        return absolutePath;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public boolean getIsReadOnly() {
        return isReadOnly;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
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

    public void setType(String type) {
        this.type = type;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setRequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public void setAbsolutePath(Path absolutePath) {
        this.absolutePath = absolutePath;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public void setReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    public void setHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void setBody(String key, String value) {
        this.body.put(key, value);
    }
}
