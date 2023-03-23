package scenes;

import client.scenes.BoardCtrl;
import client.scenes.BoardListingCtrl;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestMainCtrl {
    private MainCtrl mainCtrl;
    private ServerUtilsTestingMock server;
    private BoardCtrl boardCtrl;
    private WorkspaceCtrl workspaceCtrl;
    private Board board;

    @BeforeEach
    public void setUp() {
        this.mainCtrl = new MainCtrl();
        this.server = new ServerUtilsTestingMock();
        this.boardCtrl = mock(BoardCtrl.class);
        this.workspaceCtrl = mock(WorkspaceCtrl.class);
        this.board = new Board("testing", "");

        mainCtrl.setBoardCtrl(boardCtrl);
        mainCtrl.setWorkspaceCtrl(workspaceCtrl);

        doAnswer(invocation -> {
            board = invocation.getArgument(0);
            return board;
        }).when(boardCtrl).setBoard(Mockito.any(Board.class));

        doAnswer(invocation -> {
            board = invocation.getArgument(0);
            return board;
        }).when(workspaceCtrl).updateBoard(Mockito.any(Board.class));

        when(boardCtrl.getBoard()).thenReturn(board);
    }

    @Test
    public void testConstructor() {
        MainCtrl ctrl = new MainCtrl();
        assertNotNull(ctrl);
    }

    @Test
    public void testSwitchBoard() {
        Board newest = new Board("new", "");

        mainCtrl.switchBoard(newest);
        verify(boardCtrl, times(1)).setBoard(newest);
        assertEquals(newest, board);
    }

    @Test
    public void removeBoardFromWorkspace() {
        mainCtrl.removeFromWorkspace(board);
        verify(boardCtrl, times(1)).setBoard(null);
        verify(workspaceCtrl, times(1)).removeFromWorkspace(board);
    }

    @Test
    public void removeBoardListingCtrlFromWorkspace() {
        BoardListingCtrl mock = mock(BoardListingCtrl.class);
        mainCtrl.removeFromWorkspace(mock);
        verify(boardCtrl, times(1)).setBoard(null);
        verify(workspaceCtrl, times(1)).removeFromWorkspace(mock);
    }

    @Test
    public void updateBoardTest() {
        board.id = 1L;
        Board updated = new Board("updated", "");
        updated.id = 1L;
        mainCtrl.updateBoard(updated);

        assertEquals(board, updated);
        verify(workspaceCtrl, times(1)).updateBoard(updated);
    }

    @Test
    public void testIsPresent() {
        BoardListingCtrl listingCtrl = mock(BoardListingCtrl.class);
        when(listingCtrl.getBoard()).thenReturn(board);
        when(workspaceCtrl.getBoards()).thenReturn(List.of(listingCtrl));

        assertTrue(mainCtrl.isPresent(board));
        verify(workspaceCtrl, times(1)).getBoards();
        verify(listingCtrl, times(1)).getBoard();
    }
}
