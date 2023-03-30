package scenes.crud.board;

import client.scenes.MainCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestDeleteBoardCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private DeleteBoardCtrl deleteCtrl;
    private Board board;
    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.deleteCtrl = new DeleteBoardCtrl(server, mainCtrl);
        this.board = new Board("testing", "");
    }

    @Test
    public void testConstructor() {
        DeleteBoardCtrl ctrl = new DeleteBoardCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetBoard() {
        deleteCtrl.setBoard(board);
        assertEquals(deleteCtrl.getBoard(), board);
    }

    @Test
    public void testCancel() {
        deleteCtrl.cancel();
        verify(mainCtrl, times(1)).cancel();
    }

    @Test
    public void testConfirm() {
        deleteCtrl.setBoard(board);
        deleteCtrl.confirm();
        verify(mainCtrl, times(1)).cancel();
    }

    @Test
    public void testReset() {
        deleteCtrl.setBoard(board);
        deleteCtrl.reset();
        assertNull(deleteCtrl.getBoard());
    }
}
