package utilities;

import routes.Route;
import utilities.FileHandler;

import java.io.IOException;
import java.util.Date;

public class Logger {

    public static void log(String request, Route logRoute) throws IOException {
        Date dateTime = new Date();
        byte[] content = (dateTime + " " + request).getBytes();
        FileHandler.append(logRoute.absolutePath, content);
    }

}
