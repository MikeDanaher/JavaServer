package utilities;

import java.io.IOException;
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
}
