package scenes;

import client.MyFXML;
import client.scenes.BoardCtrl;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class TestMainCtrl {
    private MainCtrl mainCtrl;
    private ServerUtilsTesting server;
    private Stage primaryStage;
    private MyFXML fxml;

    @BeforeEach
    public void setUp() {
        this.mainCtrl = new MainCtrl();
        primaryStage = new Stage();

        WorkspaceCtrl workspaceCtrl = mock(WorkspaceCtrl.class);
        Parent rootWorkspace = new VBox();
        BoardCtrl boardCtrl = mock(BoardCtrl.class);
        Parent rootBoardCtrl = new VBox();

        var p1 = new Pair<WorkspaceCtrl, Parent>(workspaceCtrl, rootWorkspace);
        var p2 = new Pair<BoardCtrl, Parent>(boardCtrl, rootWorkspace);

        this.fxml = mock(MyFXML.class);

        mainCtrl.initialize(primaryStage, fxml, p1, p2);
    }

    @Test
    public void testConstructor() {
        MainCtrl ctrl = new MainCtrl();
        assertNotNull(ctrl);
    }
}
