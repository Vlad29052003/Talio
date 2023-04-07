package scenes.crud.task;

import client.scenes.MainCtrl;
import client.scenes.crud.task.DeleteTaskCtrl;
import commons.Task;
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
    private DeleteTaskCtrl deleteCtrl;
    private Task task;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.deleteCtrl = new DeleteTaskCtrl(server, mainCtrl);
        this.task = new Task("testing", 0, "");
    }

    @Test
    public void testConstructor() {
        DeleteTaskCtrl ctrl = new DeleteTaskCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetTaskList() {
        deleteCtrl.setTask(task);
        assertEquals(deleteCtrl.getTask(), task);
    }

    @Test
    public void testCancel() {
        deleteCtrl.cancel();
        verify(mainCtrl, times(1)).cancel();
    }

    @Test
    public void testConfirm() {
        server.addTask(task, 1L);
        deleteCtrl.setTask(task);
        deleteCtrl.delete();
        assertEquals(server.getTasks(), new ArrayList<>());
        verify(mainCtrl, times(1)).cancel();
    }
}
