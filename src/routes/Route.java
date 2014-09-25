package routes;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Route {
    public Path relativePath;
    public boolean isDirectory;
    public boolean authenticationRequired = false;
    public String authenticationString = "";

    public Route (String path, boolean directory) {
        this.relativePath = Paths.get(path);
        this.isDirectory = directory;
    }

    public Route (String path, boolean directory, boolean security, String passphrase) {
        this.relativePath = Paths.get(path);
        this.isDirectory = directory;
        this.authenticationRequired = security;
        this.authenticationString = passphrase;
    }
}
