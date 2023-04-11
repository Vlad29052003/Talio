package client.scenes.crud.tag;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Board;
import commons.Tag;
import jakarta.ws.rs.WebApplicationException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javax.inject.Inject;

public class CreateTagCtrl {
    private ServerUtils serverUtils;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    private TextField name;
    @FXML
    private ColorPicker colorPicker;

    /**
     * Creates a new {@link CreateTagCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public CreateTagCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.serverUtils = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Autofocuses the first field.
     * Sets the keyboard shortcuts for ENTER and ESC.
     */
    public void initialize() {
        this.name.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.ENTER) {
                addTag();
                event.consume();
            }
            else if (keyCode == KeyCode.ESCAPE) {
                cancel();
                event.consume();
            }
        });
    }

    /**
     * Gets the board.
     *
     * @return the board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the board.
     *
     * @param board hte board.
     */
    public void setBoard(Board board) {
        this.board = board;
        Platform.runLater(() -> name.requestFocus());
    }

    /**
     * Sends a request to create a new tag.
     */
    public void addTag() {
        if (name.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The name cannot be empty!\r");
            alert.showAndWait();
            return;
        }
        try {
            Tag tag = new Tag(name.getText(), colorPicker.getValue().toString());
            serverUtils.addTag(tag, board.id);
            cancel();
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There has been an error!\r" + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Cancels the action.
     */
    public void cancel() {
        name.setText("");
        colorPicker.setValue(Color.WHITE);
        mainCtrl.hideSecondPopup();
    }
}
