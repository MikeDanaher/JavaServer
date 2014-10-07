package fixtures;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import routes.Route;

import java.util.ArrayList;
import java.util.List;

public class TestRoutesConfig {

    public static List<Route> getRoutes(String directory) {
        List<Route> routes = new ArrayList<>();

        routes.add(formRoute(directory));
        routes.add(logRoute(directory));
        routes.add(redirectRoute(directory));
        routes.add(optionsRoute(directory));

        return routes;
    }

    private static Route formRoute(String directory) {
        String name = "form";
        String baseDirectory = directory;
        String redirect = "";
        boolean isReadOnly = false;
        return new Route(name, baseDirectory, redirect, isReadOnly);
    }

    public static Route logRoute(String directory) {
        String name = "logs";
        String baseDirectory = directory;
        String redirect = "";
        boolean isReadOnly = false;
        boolean security = true;
        String passphrase = encodePassphrase("admin:hunter2");
        return new Route(name, baseDirectory, redirect, isReadOnly, security, passphrase);
    }

    private static Route redirectRoute(String directory) {
        String name = "redirect";
        String baseDirectory = directory;
        String redirect = "/";
        boolean isReadOnly = true;
        return new Route(name, baseDirectory, redirect, isReadOnly);
    }

    private static Route optionsRoute(String directory) {
        String name = "method_options";
        String baseDirectory = directory;
        String redirect = "";
        boolean isReadOnly = true;
        return new Route(name, baseDirectory, redirect, isReadOnly);
    }

    private static String encodePassphrase(String userPassword) {
        return Base64.encode(userPassword.getBytes());
    }
}
