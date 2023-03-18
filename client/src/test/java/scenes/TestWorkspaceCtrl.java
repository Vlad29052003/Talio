package scenes;

import client.scenes.BoardDisplayWorkspace;
import client.scenes.WorkspaceCtrl;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestWorkspaceCtrl {
    private WorkspaceCtrl workspaceCtrl;
    private MainCtrlTesting mainCtrl;
    private ServerUtilsTesting server;

    @BeforeEach
    public void setUp() {
        this.mainCtrl = new MainCtrlTesting();
        this.server = new ServerUtilsTesting();
        workspaceCtrl = new WorkspaceCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        WorkspaceCtrl test = new WorkspaceCtrl(server, mainCtrl);
        assertNotNull(test);
        assertEquals(test.getServer(), server);
        assertEquals(test.getMainCtrl(), mainCtrl);
        assertEquals(test.getBoards(), new ArrayList<BoardDisplayWorkspace>());
    }

    @Test
    public void testGetterSetterBoardViewPane() {
        AnchorPane pane = new AnchorPane();
        workspaceCtrl.setBoardViewPane(pane);
        assertEquals(workspaceCtrl.getBoardViewPane(), pane);
    }

    @Test
    public void testGetterSetterBoardWorkspace() {
        VBox box = new VBox();
        workspaceCtrl.setBoardWorkspace(box);
        assertEquals(workspaceCtrl.getBoardWorkspace(), box);
    }
}
