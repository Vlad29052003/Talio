package scenes.crud.tag;

import client.scenes.MainCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.tag.EditTagCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestEditTagCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private EditTagCtrl editCtrl;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.editCtrl = new EditTagCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        EditBoardCtrl ctrl = new EditBoardCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetBoard() {
        assertNull(editCtrl.getTag());
    }

    @Test
    public void testCancel() {
        editCtrl.cancel();
        verify(mainCtrl, times(1)).hideSecondPopup();
    }
}
