package routes;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Route {
    public String name;
    public Path absolutePath;
    public String redirectPath;
    public boolean isReadOnly;
    public boolean authenticationRequired = false;
    public String authenticationString = "";

    public Route (String name, String baseDirectory, String redirect, boolean readOnly) {
        this.name = name;
        this.absolutePath = Paths.get(baseDirectory + "/" + name);
        this.redirectPath = redirect;
        this.isReadOnly = readOnly;
    }

    public Route (String name, String baseDirectory, String redirect, boolean readOnly, boolean security, String passphrase) {
        this.name = name;
        this.absolutePath = Paths.get(baseDirectory + "/" + name);
        this.redirectPath = redirect;
        this.isReadOnly = readOnly;
        this.authenticationRequired = security;
        this.authenticationString = passphrase;
    }
}
