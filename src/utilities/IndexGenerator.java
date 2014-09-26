package utilities;

import java.io.File;
import java.nio.file.Path;

public class IndexGenerator {

    public static byte[] generate(Path path) {
        StringBuilder body = new StringBuilder();
        File directory = new File(path.toString());
        File[] files = directory.listFiles();

        if (files.length != 0) {
            for (File file : files) {
                if (isValidFile(file)) {
                    body.append(createHTMLLink(file.getName()));
                }
            }
        }

        return body.toString().getBytes();
    }

    private static boolean isValidFile(File file) {
        return !file.getName().startsWith(".");
    }

    private static String createHTMLLink(String fileName) {
        String href = "<p><a href=/";
        String closeHref = ">";
        String closeTag = "</a></p>";
        return href + fileName + closeHref + fileName + closeTag;
    }

}
