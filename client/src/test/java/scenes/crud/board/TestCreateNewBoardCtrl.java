package scenes.crud.board;

import client.scenes.MainCtrl;
import client.scenes.crud.board.CreateNewBoardCtrl;
import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestCreateNewBoardCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private CreateNewBoardCtrl newBoardCtrl;
    private Board board;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.newBoardCtrl = new CreateNewBoardCtrl(server, mainCtrl);
        this.board = new Board("testing", "", "");
    }

    @Test
    public void testConstructor() {
        CreateNewBoardCtrl ctrl = new CreateNewBoardCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetBoard() {
        newBoardCtrl.setBoard(board);
        assertEquals(newBoardCtrl.getBoard(), board);
    }

    @Test
    public void testCancel() {
        newBoardCtrl.cancel();
        verify(mainCtrl, times(1)).cancel();
    }
}
