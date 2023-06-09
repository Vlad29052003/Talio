package scenes.crud.board;

import client.scenes.MainCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class TestJoinBoardCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private JoinBoardCtrl joinBoardCtrl;
    private Board board;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.joinBoardCtrl = new JoinBoardCtrl(server, mainCtrl);
        this.board = new Board("testing", "", "");
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
}
