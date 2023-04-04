package utils;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestServerUtils {
    private ServerUtils server;

    @BeforeEach
    public void setUp() {
        server = new ServerUtils();
    }

    @Test
    public void testSetGetServer() {
        server.setServer("string");
        assertEquals(server.getServer(), "string");
        server.stop();
    }
}
