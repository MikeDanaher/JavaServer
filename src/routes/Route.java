package routes;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Route {
    public String name;
    public Path absolutePath;
    public boolean isReadOnly;
    public boolean isDirectory;
    public boolean authenticationRequired = false;
    public String authenticationString = "";

    public Route (String name, String baseDirectory, boolean readOnly, boolean directory) {
        this.name = name;
        this.absolutePath = Paths.get(baseDirectory + "/" + name);
        this.isReadOnly = readOnly;
        this.isDirectory = directory;
    }

    public Route (String name, String baseDirectory, boolean readOnly, boolean directory, boolean security, String passphrase) {
        this.name = name;
        this.absolutePath = Paths.get(baseDirectory + "/" + name);
        this.isReadOnly = readOnly;
        this.isDirectory = directory;
        this.authenticationRequired = security;
        this.authenticationString = passphrase;
    }
}
