package scenes;

import client.scenes.HelpScreenCtrl;
import client.scenes.MainCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestHelpScreenCtrl {
    private MainCtrl mainCtrl;
    private ServerUtilsTestingMock server;
    private HelpScreenCtrl help;

    @BeforeEach
    public void SetUp(){
        help = new HelpScreenCtrl(server, mainCtrl);
    }

    @Test
    public void TestConstructor(){
        HelpScreenCtrl help1 = new HelpScreenCtrl(server, mainCtrl);
        assertNotNull(help1);
    }
}
