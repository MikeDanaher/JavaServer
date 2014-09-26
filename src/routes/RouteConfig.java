package routes;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.util.ArrayList;
import java.util.List;

public class RouteConfig {

    public static List<Route> getRoutes(String directory) {
        List<Route> routes = new ArrayList<>();

        routes.add(formRoute(directory));
        routes.add(logRoute(directory));

        return routes;
    }

    private static Route formRoute(String directory) {
        String path = "/form";
        String baseDirectory = directory;
        boolean isDirectory = false;
        return new Route(path, baseDirectory, isDirectory);
    }

    private static Route logRoute(String directory) {
        String path = "/logs";
        String baseDirectory = directory;
        boolean isDirectory = false;
        boolean security = true;
        String passphrase = encodePassphrase("admin:hunter2");
        return new Route(path, baseDirectory, isDirectory, security, passphrase);
    }

    private static String encodePassphrase(String userPassword) {
        return Base64.encode(userPassword.getBytes());
    }
}
