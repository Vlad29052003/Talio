package scenes;

import client.scenes.BoardCtrl;
import client.scenes.MainCtrl;
import commons.Board;
import commons.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static commons.Password.checkPassword;
import static commons.Password.getPassword;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    public void testEditBoardPassword(){
        Password pass = new Password();
        boardCtrl.editBoardPassword();
        verify(mainCtrl, times(1)).grantAdmin();

        checkPassword(getPassword());
        boardCtrl.editBoardPassword();
        verify(mainCtrl, times(1)).editBoardPassword(boardCtrl.getBoard());

    }

    @Test
    public void test(){
        boardCtrl.refresh();
    }
}
