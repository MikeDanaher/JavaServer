package routes;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.util.ArrayList;
import java.util.List;

public class RouteConfig {

    public static List<Route> getRoutes() {
        List<Route> routes = new ArrayList<>();

        routes.add(formRoute());
        routes.add(logRoute());

        return routes;
    }

    private static Route formRoute() {
        String path = "/form";
        boolean isDirectory = false;
        return new Route(path, isDirectory);
    }

    private static Route logRoute() {
        String path = "/log";
        boolean isDirectory = false;
        boolean security = true;
        String passphrase = encodePassphrase("admin:hunter2");
        return new Route(path, isDirectory, security, passphrase);
    }

    private static String encodePassphrase(String userPassword) {
        return Base64.encode(userPassword.getBytes());
    }
}
