package scenes;

import client.scenes.MainCtrl;
import client.scenes.TaskCtrl;
import commons.Board;
import commons.Task;
import commons.TaskList;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    /*
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
    */

    @Test
    public void testGetRoot() {
        assertNull(taskController.getRoot());
    }

    @Test
    public void testOpenTaskOverview() {
        taskController.openTaskOverview();
        verify(mainCtrl, times(1)).openTask(null);
    }

    @Test
    public void testGetBackgroundStyle() {
        Task task = new Task();
        task.color = Color.BEIGE.toString();
        taskController.setTask(task);
        assertEquals("-fx-background-color: 0xf5f5dcff;", taskController.getBackgroundStyle());
    }

    @Test
    public void testGetBackgroundStyle2() {
        Task task = new Task();
        task.color = "";
        taskController.setTask(task);
        assertEquals("-fx-background-color: #f4f4f4;", taskController.getBackgroundStyle());
    }

    @Test
    public void testGetFontColor() {
        Task task = new Task();
        task.color = Color.BEIGE.toString();
        taskController.setTask(task);
        assertEquals("-fx-text-fill: #000000;", taskController.getFontStyle());
    }

    @Test
    public void testGetFontColor2() {
        Task task = new Task();
        task.color = "";
        taskController.setTask(task);
        assertEquals("-fx-text-fill: #000000;", taskController.getFontStyle());
    }

    @Test
    public void testGetFontColor3() {
        Task task = new Task();
        task.color = Color.BLACK.toString();
        taskController.setTask(task);
        assertEquals("-fx-text-fill: #ffffff;", taskController.getFontStyle());
    }

    @Test
    public void testSendMoveRequest() {
        Task task = new Task();
        task.id = 1L;
        TaskList tl = new TaskList();
        task.setTaskList(tl);
        tl.id = 1L;
        taskController.setTask(task);
        taskController.sendMoveRequest(1);
        assertTrue(server.getCalledMethods().contains("dragAndDrop"));
    }

    @Test
    public void testTagOverview() {
        Task task = new Task();
        TaskList tl = new TaskList();
        Board b = new Board();
        tl.setBoard(b);
        task.setTaskList(tl);
        taskController.setTask(task);
        taskController.tagOverview();
        verify(mainCtrl, times(1)).tagOverview(b);
    }
}
