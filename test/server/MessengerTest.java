package server;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MessengerTest {

    String message = "Hello!";
    Messenger mgr = new Messenger(message);

    @Test
    public void testPrintMessage() throws Exception {
        assertEquals(message, mgr.printMessage());
    }
}