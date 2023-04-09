package utils;

import client.utils.ServerURL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestServerURL {
    private ServerURL serverURL;

    @BeforeEach
    public void setUp() {
        serverURL = new ServerURL("localhost", 8080);
    }

    @Test
    public void testParseURL() {
        ServerURL test = ServerURL.parseURL("localhost:8080");
        assertEquals(test.port, 8080);
        assertEquals(test.host, "localhost");
    }

    @Test
    public void testMalformedURL() {
        ServerURL test = ServerURL.parseURL("ht:tp//www.example.com");
        assertNull(test);
    }
}
