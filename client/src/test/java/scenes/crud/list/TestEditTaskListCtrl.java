package scenes.crud.list;

import client.scenes.MainCtrl;
import client.scenes.crud.tasklists.EditTaskListCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class TestEditTaskListCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private EditTaskListCtrl editCtrl;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.editCtrl = new EditTaskListCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        EditTaskListCtrl ctrl = new EditTaskListCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetBoard() {
        assertNull(editCtrl.getTaskList());
    }

//    @Test
//    public void testCancel() {
//        editTaskCtrl.cancel();
//        verify(mainCtrl, times(1)).cancel();
//    }
}
