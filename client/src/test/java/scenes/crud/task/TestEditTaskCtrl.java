package scenes.crud.task;

import client.scenes.MainCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.tasklists.EditTaskListCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;

public class TestEditTaskCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private EditTaskListCtrl editTaskListCtrl;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.editTaskListCtrl = new EditTaskListCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        EditBoardCtrl ctrl = new EditBoardCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetTaskList() {
        assertNull(editTaskListCtrl.getTaskList());
    }

//    @Test
//    public void testCancel() {
//        editTaskListCtrl.cancel();
//        verify(mainCtrl, times(1)).cancel();
//    }
}
