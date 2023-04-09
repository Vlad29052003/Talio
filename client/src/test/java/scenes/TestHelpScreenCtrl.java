package scenes;

import client.scenes.HelpScreenCtrl;
import client.scenes.MainCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestHelpScreenCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private HelpScreenCtrl helpCtrl;

    @BeforeEach
    public void setUp() {
        server = new ServerUtilsTestingMock();
        mainCtrl = mock(MainCtrl.class);
        helpCtrl = new HelpScreenCtrl(server, mainCtrl);
    }

    @Test
    public void testCancel() {
        helpCtrl.cancel();
        verify(mainCtrl, times(1)).hidePopup();
    }
}
