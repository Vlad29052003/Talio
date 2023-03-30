package scenes.crud.task;

import client.scenes.MainCtrl;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.task.CreateTaskCtrl;
import commons.Task;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        CreateNewBoardCtrl ctrl = new CreateNewBoardCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetTaskList() {
        TaskList tl = new TaskList();
        createTaskCtrl.setTaskList(tl);
        assertEquals(createTaskCtrl.getTaskList(), tl);
    }
}
