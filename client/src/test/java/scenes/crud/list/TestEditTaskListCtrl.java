package scenes.crud.list;

import client.scenes.MainCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.task.EditTaskCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestEditTaskListCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private EditTaskCtrl editTaskCtrl;
    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.editTaskCtrl = new EditTaskCtrl(mainCtrl, server);
    }

    @Test
    public void testConstructor() {
        EditBoardCtrl ctrl = new EditBoardCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetBoard() {
        assertNull(editTaskCtrl.getTask());
    }

    @Test
    public void testCancel() {
        editTaskCtrl.cancel();
        verify(mainCtrl, times(1)).cancel();
    }
}
