package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WorkspaceCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private List<BoardCtrl> boards;
    private int inc;
    @FXML
    private AnchorPane boardViewPane;
    @FXML
    private VBox boardButtons;



    @Inject
    public WorkspaceCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        inc = 0;
        boards = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    // Package private: only used in MainCtrl during binding.
    void setBoardView(Parent boardRoot) {
        this.boardViewPane.getChildren().add(boardRoot);
        AnchorPane.setTopAnchor(boardRoot, 0.0);
        AnchorPane.setLeftAnchor(boardRoot, 0.0);
        AnchorPane.setRightAnchor(boardRoot, 0.0);
        AnchorPane.setBottomAnchor(boardRoot, 0.0);
    }

    public void addBoard() {
        {
            Board newest = new Board("name" + inc, "");
            inc ++;

            try {
                newest = server.addBoard(newest);
            } catch (WebApplicationException e) {

                var alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }

            System.out.println(newest);

            Button nBoard = new Button(newest.name + " (" + newest.id + ")");
            BoardCtrl newBoard = new BoardCtrl(server, mainCtrl, newest);
            boards.add(newBoard);
            nBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    mainCtrl.switchBoard(newBoard);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            boardButtons.getChildren().add(nBoard);
        }
    }
}
