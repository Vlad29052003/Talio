package scenes.crud.board;

import client.scenes.MainCtrl;
import client.scenes.crud.board.YouHavePermissionCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class TestYouHavePermissionCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private YouHavePermissionCtrl youHavePermissionCtrl;
    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.youHavePermissionCtrl = new YouHavePermissionCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        YouHavePermissionCtrl ctrl = new YouHavePermissionCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }
}
