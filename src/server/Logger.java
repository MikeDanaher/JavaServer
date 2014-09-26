package server;

import utilities.FileHandler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

public class Logger {

    public static void log(String request, Path logFile) throws IOException {
        Date dateTime = new Date();
        byte[] content = (dateTime + " " + request).getBytes();
        FileHandler.append(logFile, content);
    }

}
