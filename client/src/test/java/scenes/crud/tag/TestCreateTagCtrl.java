package scenes.crud.tag;

import client.scenes.MainCtrl;
import client.scenes.crud.tag.CreateTagCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class TestCreateTagCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private CreateTagCtrl createTagCtrl;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.createTagCtrl = new CreateTagCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        CreateTagCtrl ctrl = new CreateTagCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetTaskList() {
        assertNull(createTagCtrl.getBoard());
    }
}
