package utilities;

import java.util.Arrays;

public class ArgsParser {
    public static final String PORT_FLAG         = "-p";
    public static final int    DEFAULT_PORT      = 5000;
    public static final String DIRECTORY_FLAG    = "-d";
    public static final String DEFAULT_DIRECTORY = "public";
    private String[] args;

    public ArgsParser(String[] args) {
        this.args = args;
    }

    public int getPort() {
        int portNumber = DEFAULT_PORT;
        if (hasFlag(PORT_FLAG)) {
            portNumber = Integer.parseInt(getValue(PORT_FLAG));
        }
        return portNumber;
    }

    public String getDirectory() {
        String directory = DEFAULT_DIRECTORY;
        if (hasFlag(DIRECTORY_FLAG)) {
            directory = getValue(DIRECTORY_FLAG);
        }
        return directory;
    }

    private boolean hasFlag(String key) {
        return Arrays.asList(args).contains(key);
    }

    private String getValue(String key) {
        int argsKey = Arrays.asList(args).indexOf(key);
        return args[argsKey + 1];
    }
}
