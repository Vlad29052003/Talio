package scenes.crud.list;

import client.scenes.MainCtrl;
import client.scenes.crud.tasklists.CreateTaskListCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class TestCreateTaskListCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private CreateTaskListCtrl createTaskListCtrl;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.createTaskListCtrl = new CreateTaskListCtrl(server, mainCtrl);
    }

    @Test
    public void testGetBoard() {
        assertNull(createTaskListCtrl.getBoard());
    }

    @Test
    public void testConstructor() {
        CreateTaskListCtrl ctrl = new CreateTaskListCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }
}
