package server;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArgsParserTest {

    @Test
    public void testGetDefaultPort() {
        String[] args = {""};
        ArgsParser arguments = new ArgsParser(args);
        assertEquals(5000, arguments.getPort());
    }

    @Test
    public void testGetDefaultDirectory() {
        String[] args = {""};
        ArgsParser arguments = new ArgsParser(args);
        assertEquals("public", arguments.getDirectory());
    }

    @Test
    public void testGetAssignedPort() {
        String[] args = {"-p", "5050"};
        ArgsParser arguments = new ArgsParser(args);
        assertEquals(5050, arguments.getPort());
    }

    @Test
    public void testGetAssignedDirectory() {
        String[] args = {"-d", "/foo/bar"};
        ArgsParser arguments = new ArgsParser(args);
        assertEquals("/foo/bar", arguments.getDirectory());
    }

}