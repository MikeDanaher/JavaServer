package utilities;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class IndexGeneratorTest {

    private String baseDirectory = "/Users/mikedanaher/Dev/8thLight/JavaServer/test/fixtures/";

    @Test
    public void testContainsFiles() throws IOException {
        byte[] index = IndexGenerator.generate(Paths.get(baseDirectory));
        String indexText = new String(index, "UTF-8");

        assertThat(indexText, containsString("file1"));
        assertThat(indexText, containsString("file2"));
    }

    @Test
    public void testContainsLinks() throws IOException {
        byte[] index = IndexGenerator.generate(Paths.get(baseDirectory));
        String indexText = new String(index, "UTF-8");

        assertThat(indexText, containsString("href"));
    }
}
