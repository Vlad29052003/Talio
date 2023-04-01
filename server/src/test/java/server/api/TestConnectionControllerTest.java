package server.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConnectionControllerTest {
    @Test
    public void testTestConnection() {
        TestConnectionController ctrl = new TestConnectionController();
        assertEquals(ctrl.testConnection(), ResponseEntity.ok().build());
    }
}
