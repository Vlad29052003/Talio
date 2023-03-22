package scenes.crud.board;

import client.scenes.MainCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTesting;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestJoinBoardCtrl {
    private ServerUtilsTesting server;
    private MainCtrl mainCtrl;
    private JoinBoardCtrl joinBoardCtrl;
    private Board board;
    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTesting();
        this.mainCtrl = mock(MainCtrl.class);
        this.joinBoardCtrl = new JoinBoardCtrl(server, mainCtrl);
        this.board = new Board("testing", "");
    }

    @Test
    public void testConstructor() {
        JoinBoardCtrl ctrl = new JoinBoardCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetBoard() {
        joinBoardCtrl.setBoard(board);
        assertEquals(joinBoardCtrl.getBoard(), board);
    }

    @Test
    public void testCancel() {
        joinBoardCtrl.cancel();
        verify(mainCtrl, times(1)).cancel();
    }
}
