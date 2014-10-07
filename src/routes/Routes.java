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
        String redirect = "";
        boolean isReadOnly = true;
        Route rootRoute = new Route(name, baseDirectory, redirect, isReadOnly);
        validRoutes.put(name, rootRoute);
    }

    private void buildConfigRoutes() {
        if (!routeConfig.isEmpty()) {
            for (Route route : routeConfig) {
                validRoutes.put("/" + route.name, route);
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
        String redirect = "";
        boolean isReadOnly = true;
        return new Route(name, baseDirectory, redirect, isReadOnly);
    }

}
