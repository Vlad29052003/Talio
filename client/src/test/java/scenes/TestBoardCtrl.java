package scenes;

import client.scenes.BoardCtrl;
import client.scenes.MainCtrl;
import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

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
        this.board = new Board("testing", "");
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
}