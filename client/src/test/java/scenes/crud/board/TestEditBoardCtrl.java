package scenes.crud.board;

import client.scenes.MainCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestEditBoardCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private EditBoardCtrl editBoardCtrl;
    private Board board;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.editBoardCtrl = new EditBoardCtrl(server, mainCtrl);
        this.board = new Board("testing", "");
    }

    @Test
    public void testConstructor() {
        EditBoardCtrl ctrl = new EditBoardCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetBoard() {
        assertNull(editBoardCtrl.getBoard());
    }

    @Test
    public void testCancel() {
        editBoardCtrl.cancel();
        verify(mainCtrl, times(1)).cancel();
    }
}
