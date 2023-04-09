package scenes.crud.admin;

import client.scenes.MainCtrl;
import client.scenes.crud.admin.PermissionAdminCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class TestPermissionAdminCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private PermissionAdminCtrl permissionAdminCtrl;
    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.permissionAdminCtrl = new PermissionAdminCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        PermissionAdminCtrl ctrl = new PermissionAdminCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }
}
