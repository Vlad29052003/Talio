package scenes.crud.task;

import client.scenes.MainCtrl;
import client.scenes.crud.task.EditTaskCtrl;
import commons.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestEditTaskCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private EditTaskCtrl editTaskCtrl;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.editTaskCtrl = new EditTaskCtrl(server, mainCtrl);
    }

    @Test
    public void testSetWithoutRefresh() {
        Task test = new Task();
        editTaskCtrl.setTaskWithoutRefreshing(test);
        assertEquals(editTaskCtrl.getTask(), test);
    }

    @Test
    public void testConstructor() {
        EditTaskCtrl ctrl = new EditTaskCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetTaskList() {
        assertNull(editTaskCtrl.getTask());
    }

    @Test
    public void testCancel() {
        editTaskCtrl.cancel();
        verify(mainCtrl, times(1)).cancel();
    }
}
