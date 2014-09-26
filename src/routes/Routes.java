package routes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Routes {
    public String baseDirectory;
    private List<Route> routeConfig = new ArrayList<>();
    private Map<String, Route> validRoutes = new HashMap<>();


    public Routes(String directory) {
        this.baseDirectory = directory;
    }

    public Routes(String directory, List<Route> config) {
        this.baseDirectory = directory;
        this.routeConfig = config;
    }

    public Map<String, Route> getValidRoutes() {
        buildRootRoute();
        buildFileRoutes();
        buildConfigRoutes();
        return validRoutes;
    }

    private void buildRootRoute() {
        String name = "/";
        boolean isDirectory = true;
        Route rootRoute = new Route(name, baseDirectory, isDirectory);
        validRoutes.put(name, rootRoute);
    }

    private void buildConfigRoutes() {
        if (!routeConfig.isEmpty()) {
            for (Route route : routeConfig) {
                validRoutes.put(route.relativePath.toString(), route);
            }
        }
    }

    private void buildFileRoutes() {
        File directory = new File(baseDirectory);
        File[] allFiles = directory.listFiles();

        if (allFiles.length != 0) {
            for (File file : allFiles) {
                validRoutes.put("/" + file.getName(), buildFileRoute(file));
            }
        }
    }

    private Route buildFileRoute(File file) {
        String name = file.getName();
        boolean isDirectory = false;
        return new Route(name, baseDirectory, isDirectory);
    }

}
