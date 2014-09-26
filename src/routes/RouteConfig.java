package routes;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.util.ArrayList;
import java.util.List;

public class RouteConfig {

    public static List<Route> getRoutes(String directory) {
        List<Route> routes = new ArrayList<>();

        routes.add(formRoute(directory));
        routes.add(logRoute(directory));
        routes.add(redirectRoute(directory));

        return routes;
    }

    private static Route formRoute(String directory) {
        String name = "form";
        String baseDirectory = directory;
        boolean isReadOnly = false;
        boolean isDirectory = false;
        return new Route(name, baseDirectory, isReadOnly, isDirectory);
    }

    public static Route logRoute(String directory) {
        String name = "logs";
        String baseDirectory = directory;
        boolean isReadOnly = false;
        boolean isDirectory = false;
        boolean security = true;
        String passphrase = encodePassphrase("admin:hunter2");
        return new Route(name, baseDirectory, isReadOnly, isDirectory, security, passphrase);
    }

    private static Route redirectRoute(String directory) {
        String name = "redirect";
        String baseDirectory = directory;
        boolean isReadOnly = true;
        boolean isDirectory = false;
        return new Route(name, baseDirectory, isReadOnly, isDirectory);
    }

    private static String encodePassphrase(String userPassword) {
        return Base64.encode(userPassword.getBytes());
    }
}
