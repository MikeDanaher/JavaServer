package utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileHandler {

    public static void writeOver(Path path, byte[] content) throws IOException {
        Files.write(path, content);
    }

    public static void append(Path path, byte[] content) throws IOException {
        Files.write(path, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static void delete(Path path) throws IOException {
        Files.delete(path);
    }

    public static byte[] read(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    public static byte[] readPartial(Path path, String range) throws IOException {
        String[] ranges = parseRange(range);
        int offset = getOffset(ranges);
        int length = getLength(ranges, path);
        byte[] content = new byte[length];

        InputStream is = Files.newInputStream(path);
        is.read(content, offset, length - offset);
        return content;
    }

    private static String[] parseRange(String range) {
        return range.substring("bytes=".length()).split("-");
    }

    private static int getOffset(String[] ranges) {
        return Integer.valueOf(ranges[0]);
    }

    private static int getLength(String[] ranges, Path path) {
        if (ranges.length > 1) {
            return Integer.valueOf(ranges[1]);
        } else {
            File file = new File(path.toString());
            return (int)file.length();
        }
    }
}
