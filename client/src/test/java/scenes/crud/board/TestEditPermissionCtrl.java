package scenes.crud.board;

import client.scenes.MainCtrl;
import client.scenes.crud.board.EditPermissionCtrl;
import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class TestEditPermissionCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private EditPermissionCtrl editPermissionCtrl;
    private Board board;
    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.editPermissionCtrl = new EditPermissionCtrl(server, mainCtrl);
        this.board = new Board("testing");
    }

    @Test
    public void testConstructor() {
        EditPermissionCtrl ctrl = new EditPermissionCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetBoard() {
        editPermissionCtrl.setBoard(board);
        assertEquals(editPermissionCtrl.getBoard(), board);
    }
}
