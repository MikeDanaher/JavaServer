package utilities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CaseSwitcherTest {

    @Test
    public void testGetProperCase() {
        String method = "OPTIONS";
        assertEquals("Options", CaseSwitcher.toProperCase(method));
    }
}