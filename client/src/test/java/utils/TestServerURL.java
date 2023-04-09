package utils;

import client.utils.ServerURL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestServerURL {
    private ServerURL url;

    @BeforeEach
    public void setUp(){
        url = new ServerURL("localhost", 8080);
    }

    @Test
    public void TestConstructor(){
        ServerURL url1 = new ServerURL("1",1);
        assertEquals(url1.host, "1");
        assertEquals(url1.port, 1);
    }

    @Test
    public void TestToString(){
        assertNotNull(url.toString());
    }

    @Test
    public void TestParseURL(){
        url = ServerURL.parseURL("wadas:8080:2112432.sdf");
        assertNull(url);

        url = ServerURL.parseURL("localhost:8080");
        assertEquals(url.port, 8080);
        assertEquals(url.host, "localhost");
    }
}
