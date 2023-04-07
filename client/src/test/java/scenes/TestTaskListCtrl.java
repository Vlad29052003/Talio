package scenes;

import client.scenes.MainCtrl;
import client.scenes.TaskListCtrl;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestTaskListCtrl {
    private MainCtrl mainCtrl;
    private ServerUtilsTestingMock server;
    private TaskListCtrl listCtrl;
    private TaskList list;

    @BeforeEach
    public void setUp() {
        this.mainCtrl = mock(MainCtrl.class);
        this.server = new ServerUtilsTestingMock();
        this.listCtrl = new TaskListCtrl(server, mainCtrl);
        this.list = new TaskList("test");
    }


    @Test
    public void testConstructor() {
        TaskListCtrl test = new TaskListCtrl(server, mainCtrl);
        assertNotNull(test);
    }

    @Test
    public void testGetRoot() {
        assertNull(listCtrl.getRoot());
    }

    @Test
    public void testGetSetRoot() {
        listCtrl.setTaskList(list);
        assertEquals(list, listCtrl.getTaskList());
    }

    @Test
    public void testEdit() {
        listCtrl.setTaskList(list);
        listCtrl.edit();
        verify(mainCtrl, times(1)).editTaskList(list);
    }

    @Test
    public void testDelete() {
        listCtrl.setTaskList(list);
        listCtrl.delete();
        verify(mainCtrl, times(1)).deleteTaskList(list);
    }

    @Test
    public void testAddTask() {
        listCtrl.setTaskList(list);
        listCtrl.addTask();
        verify(mainCtrl, times(1)).addTask(list);
    }

    @Test
    public void testSendMoveRequest() {
        TaskList taskList = new TaskList();
        taskList.id = 0L;
        listCtrl.setTaskList(taskList);
        listCtrl.sendMoveRequest(0L, 1);
        assertEquals(server.getCalledMethods(), List.of("dragAndDrop"));
    }
}
