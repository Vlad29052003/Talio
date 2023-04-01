package client.scenes.crud.tag;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Board;
import commons.Tag;
import commons.Task;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javax.inject.Inject;

public class CreateTagCtrl {
    private ServerUtils serverUtils;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    private TextField name;

    @Inject
    private CreateTagCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.serverUtils = server;
        this.mainCtrl = mainCtrl;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void addTag() {
        if (name.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There name cannot be empty!\r");
            alert.showAndWait();
            return;
        }
        try {
            Tag tag = new Tag(name.getText(), "");
            tag = serverUtils.addTag(tag, board.id);
            board.tags.add(tag);
            mainCtrl.refreshTagOverview();
            cancel();
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There has been an error!\r" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void cancel() {
        name.setText("");
        mainCtrl.hideSecondPopup();
    }
}
