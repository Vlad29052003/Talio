package scenes.crud.admin;

import client.scenes.MainCtrl;
import client.scenes.crud.admin.EditBoardPasswordCtrl;
import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestEditBoardPasswordCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private EditBoardPasswordCtrl editBoardPasswordCtrl;
    private Board board;
    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.editBoardPasswordCtrl = new EditBoardPasswordCtrl(server, mainCtrl);
        this.board = new Board("testing", "");
    }

    @Test
    public void testConstructor() {
        EditBoardPasswordCtrl ctrl = new EditBoardPasswordCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetBoard() {
        editBoardPasswordCtrl.setBoard(board);
        assertEquals(editBoardPasswordCtrl.getBoard(), board);
    }

    @Test
    public void testCancel(){
        editBoardPasswordCtrl.cancel();
        verify(mainCtrl, times(1)).cancel();
    }
}
