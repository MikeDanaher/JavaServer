package routes;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Route {
    public String name;
    public Path relativePath;
    public Path absolutePath;
    public boolean isDirectory;
    public boolean authenticationRequired = false;
    public String authenticationString = "";

    public Route (String name, String baseDirectory, boolean directory) {
        this.name = name;
        this.relativePath = Paths.get("/" + name);
        this.absolutePath = Paths.get(baseDirectory + "/" + name);
        this.isDirectory = directory;
    }

    public Route (String name, String baseDirectory, boolean directory, boolean security, String passphrase) {
        this.name = name;
        this.relativePath = Paths.get("/" + name);
        this.absolutePath = Paths.get(baseDirectory + "/" + name);
        this.isDirectory = directory;
        this.authenticationRequired = security;
        this.authenticationString = passphrase;
    }
}
