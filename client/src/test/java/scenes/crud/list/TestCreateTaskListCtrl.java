package scenes.crud.list;

import client.scenes.MainCtrl;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.task.CreateTaskCtrl;
import client.scenes.crud.tasklists.CreateTaskListCtrl;
import commons.Board;
import commons.Task;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class TestCreateTaskListCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private CreateTaskListCtrl createTaskListCtrl;
    private Board b;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.createTaskListCtrl = new CreateTaskListCtrl(server, mainCtrl);
        this.b = new Board("testing", "");
    }

    @Test
    public void testConstructor() {
        CreateNewBoardCtrl ctrl = new CreateNewBoardCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetBoard() {
        createTaskListCtrl.setBoard(b);
        assertEquals(createTaskListCtrl.getBoard(), b);
    }
}
