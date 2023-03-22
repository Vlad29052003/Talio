package scenes;

import client.scenes.BoardDisplayWorkspace;
import client.scenes.MainCtrl;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestBoardDisplayWorkspace {
    private BoardDisplayWorkspace display;
    private MainCtrl mainCtrl;
    private ServerUtilsTestingMock server;
    private VBox root;

    @BeforeEach
    public void setUp() {
        this.mainCtrl = mock(MainCtrl.class);
        this.server = new ServerUtilsTestingMock();
        this.display = new BoardDisplayWorkspace(server, mainCtrl);
        this.root = new VBox();
    }

    @Test
    public void testConstructor() {
        BoardDisplayWorkspace test = new BoardDisplayWorkspace(server, mainCtrl);
        assertNotNull(test);
    }

    @Test
    public void testGetSetRoot() {
        display.setRoot(root);
        assertEquals(display.getRoot(), root);
    }

    @Test
    public void testView() {
        display.view();
        verify(mainCtrl, times(1)).switchBoard(display.getBoard());
    }

    @Test
    public void testDelete() {
        display.delete();
        verify(mainCtrl, times(1)).deleteBoard(display.getBoard());
    }

    @Test
    public void testRemove() {
        display.remove();
        verify(mainCtrl, times(1)).removeFromWorkspace(display);
    }

    @Test
    public void testEdit() {
        display.edit();
        verify(mainCtrl, times(1)).editBoard(display.getBoard());
    }
}
