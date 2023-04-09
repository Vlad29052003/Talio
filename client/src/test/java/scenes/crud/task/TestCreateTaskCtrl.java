package scenes.crud.task;

import client.scenes.MainCtrl;
import client.scenes.crud.task.CreateTaskCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class TestCreateTaskCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private CreateTaskCtrl createTaskCtrl;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.createTaskCtrl = new CreateTaskCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        CreateTaskCtrl ctrl = new CreateTaskCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetTaskList() {
        assertNull(createTaskCtrl.getTaskList());
    }
}
