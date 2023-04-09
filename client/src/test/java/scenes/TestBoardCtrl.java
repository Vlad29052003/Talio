package scenes;

import client.scenes.BoardCtrl;
import client.scenes.MainCtrl;
import commons.Board;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestBoardCtrl {
    private BoardCtrl boardCtrl;
    private MainCtrl mainCtrl;
    private ServerUtilsTestingMock server;
    private Board board;

    @BeforeEach
    public void setUp() {
        this.mainCtrl = mock(MainCtrl.class);
        this.server = new ServerUtilsTestingMock();
        this.boardCtrl = new BoardCtrl(server, mainCtrl);
        this.board = new Board("testing", "", "");
    }

    @Test
    public void testConstructor() {
        BoardCtrl test = new BoardCtrl(server, mainCtrl);
        assertNotNull(test);
    }

    @Test
    public void testGetBoard() {
        assertNull(boardCtrl.getBoard());
    }

    @Test
    public void testEditBoardPassword() {
        when(mainCtrl.getAdmin()).thenReturn(true);
        boardCtrl.editBoardPassword();
        verify(mainCtrl, times(1)).getAdmin();
        verify(mainCtrl, times(1)).editBoardPassword(boardCtrl.getBoard());
    }

    @Test
    public void testAddTaskList() {
        boardCtrl.addTaskList();
        verify(mainCtrl, times(0)).addTaskList(board);
    }

    @Test
    public void testTagOverview() {
        when(mainCtrl.getAdmin()).thenReturn(true);
        boardCtrl.tagOverview();
        boardCtrl.resetFocus();
        TaskList list = new TaskList();
        board.lists.add(list);
        boardCtrl.setBoardWithoutRefresh(board);
        list.id = 1L;
        boardCtrl.getNextIndex(list, 5);
        boardCtrl.getNeighbourIndex(list, 5, true);
        boardCtrl.getNeighbourIndex(list, 5, false);
        verify(mainCtrl, times(1)).tagOverview(null);
    }
}
