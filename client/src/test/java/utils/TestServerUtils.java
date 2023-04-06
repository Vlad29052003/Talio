package utils;

import client.utils.ServerURL;
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
        ServerURL url = new ServerURL("string", 123);

        server.setServer(url);
        assertEquals(server.getServer(), url);
        assertEquals(server.getServerAddress(), "http://string:123");
        server.stop();
    }
}
