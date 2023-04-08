package scenes.crud.admin;

import client.scenes.MainCtrl;
import client.scenes.crud.admin.GrantAdminCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class TestGrantAdminCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private GrantAdminCtrl grantAdminCtrl;
    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.grantAdminCtrl = new GrantAdminCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        GrantAdminCtrl ctrl = new GrantAdminCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }
}
