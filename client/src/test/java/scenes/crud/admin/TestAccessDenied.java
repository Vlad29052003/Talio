package scenes.crud.admin;

import client.scenes.MainCtrl;
import client.scenes.crud.admin.AccessDeniedCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestAccessDenied {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private AccessDeniedCtrl accessDeniedCtrl;
    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.accessDeniedCtrl = new AccessDeniedCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        AccessDeniedCtrl ctrl = new AccessDeniedCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testOk(){
        accessDeniedCtrl.ok();
        verify(mainCtrl, times(1)).cancel();
    }
}
