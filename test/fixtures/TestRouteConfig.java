package fixtures;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import routes.Route;

import java.util.ArrayList;
import java.util.List;

public class TestRouteConfig {

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
        String passphrase = Base64.encode("admin:hunter2".getBytes());
        return new Route(path, isDirectory, security, passphrase);
    }
}
