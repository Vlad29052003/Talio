package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkspaceCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private BoardCtrl boardCtrl;

    @FXML
    private AnchorPane boardViewPane;

    @FXML
    private Button buttonB1;

    @FXML
    private Button buttonB2;

    @FXML
    private Button buttonB3;

    @FXML
    private Button buttonB4;

    @Inject
    public WorkspaceCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.buttonB1.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> this.mainCtrl.setBoard("Board1"));
        this.buttonB2.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> this.mainCtrl.setBoard("Board2"));
        this.buttonB3.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> this.mainCtrl.setBoard("Board3"));
        this.buttonB4.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> this.mainCtrl.setBoard("Board4"));
    }

    // Package private: only used in MainCtrl during binding.
    void setBoardView(Parent boardRoot) {
        this.boardViewPane.getChildren().add(boardRoot);
        AnchorPane.setTopAnchor(boardRoot, 0.0);
        AnchorPane.setLeftAnchor(boardRoot, 0.0);
        AnchorPane.setRightAnchor(boardRoot, 0.0);
        AnchorPane.setBottomAnchor(boardRoot, 0.0);
    }
}
