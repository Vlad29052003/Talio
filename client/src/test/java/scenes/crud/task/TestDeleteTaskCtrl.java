package scenes.crud.task;

import client.scenes.MainCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.tasklists.DeleteTaskListCtrl;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestDeleteTaskCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private DeleteTaskListCtrl deleteCtrl;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.deleteCtrl = new DeleteTaskListCtrl(server, mainCtrl);
        this.taskList = new TaskList("testing");
    }

    @Test
    public void testConstructor() {
        DeleteBoardCtrl ctrl = new DeleteBoardCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetTaskList() {
        deleteCtrl.setTaskList(taskList);
        assertEquals(deleteCtrl.getTaskList(), taskList);
    }

    @Test
    public void testCancel() {
        deleteCtrl.cancel();
        verify(mainCtrl, times(1)).cancel();
    }

    @Test
    public void testConfirm() {
        server.addTaskList(taskList, 1L);
        deleteCtrl.setTaskList(taskList);
        deleteCtrl.confirm();
        assertEquals(server.getTasks(), new ArrayList<>());
        verify(mainCtrl, times(1)).cancel();
    }
}
