package scenes.crud.task;

import client.scenes.MainCtrl;
import client.scenes.crud.task.OpenTaskCtrl;
import commons.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestOpenTaskController {
    private OpenTaskCtrl openTaskCtrl;
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private OpenTaskCtrl.SubTask subTask;
    private Task test;

    @BeforeEach
    public void setUp() {
        test = new Task("task", 0, "");
        server = new ServerUtilsTestingMock();
        mainCtrl = mock(MainCtrl.class);
        openTaskCtrl = new OpenTaskCtrl(server, mainCtrl);
        subTask = new OpenTaskCtrl.SubTask("s", false, test, openTaskCtrl);
    }

    @Test
    public void testCancel() {
        openTaskCtrl.cancel();
        verify(mainCtrl, times(1)).hidePopup();
    }

    @Test
    public void testSubTaskControllers() {
        OpenTaskCtrl.SubTask testSubTask;
        testSubTask = new OpenTaskCtrl.SubTask("s", false, test, openTaskCtrl);
        assertNotNull(testSubTask);
        testSubTask = new OpenTaskCtrl.SubTask("s1", test, openTaskCtrl);
        assertNotNull(testSubTask);
    }

    @Test
    public void testSubtaskGetName() {
        assertEquals(subTask.getName(), "s");
    }

    @Test
    public void testSubtaskToString() {
        assertEquals(subTask.toString(), "s");
    }
}
