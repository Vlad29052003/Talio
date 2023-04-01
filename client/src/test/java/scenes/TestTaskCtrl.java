package scenes;

import client.scenes.MainCtrl;
import client.scenes.TaskCtrl;
import commons.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestTaskCtrl {
    private MainCtrl mainCtrl;
    private ServerUtilsTestingMock server;
    private TaskCtrl taskController;
    private Task task;

    @BeforeEach
    public void setUp() {
        this.mainCtrl = mock(MainCtrl.class);
        this.server = new ServerUtilsTestingMock();
        this.taskController = new TaskCtrl(server, mainCtrl);
        this.task = new Task("task1", 0, "");
    }

    @Test
    public void testConstructor() {
        TaskCtrl test = new TaskCtrl(server, mainCtrl);
        assertNotNull(test);
    }

    @Test
    public void testGetSetTask() {
        taskController.setTask(task);
        assertEquals(taskController.getTask(), task);
    }

    @Test
    public void testEdit() {
        taskController.setTask(task);
        taskController.edit();
        verify(mainCtrl, times(1)).editTask(task);
    }

    @Test
    public void testDelete() {
        taskController.setTask(task);
        taskController.delete();
        verify(mainCtrl, times(1)).deleteTask(task);
    }

    @Test
    public void testGetRoot() {
        assertNull(taskController.getRoot());
    }
}
