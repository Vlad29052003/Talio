package scenes;

import client.scenes.BoardListingCtrl;
import client.scenes.MainCtrl;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestBoardListingCtrl {
    private BoardListingCtrl display;
    private MainCtrl mainCtrl;
    private ServerUtilsTestingMock server;
    private VBox root;

    @BeforeEach
    public void setUp() {
        this.mainCtrl = mock(MainCtrl.class);
        this.server = new ServerUtilsTestingMock();
        this.display = new BoardListingCtrl(server, mainCtrl);
        this.root = new VBox();
    }

    @Test
    public void testConstructor() {
        BoardListingCtrl test = new BoardListingCtrl(server, mainCtrl);
        assertNotNull(test);
    }

    @Test
    public void testGetSetRoot() {
        assertNull(display.getRoot());
    }

    @Test
    public void testView() {
        display.view();
        verify(mainCtrl, times(1)).switchBoard(display.getBoard());
    }

    @Test
    public void testDelete() {
        //to be implemented with the other edit tests
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
