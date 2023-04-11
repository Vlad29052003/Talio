package client.scenes.crud.tag;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Tag;
import jakarta.ws.rs.WebApplicationException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javax.inject.Inject;

public class DeleteTagCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Tag tag;
    @FXML
    private AnchorPane root;

    /**
     * Creates a new {@link DeleteTagCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public DeleteTagCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Sets the keyboard shortcuts for ENTER and ESC.
     */
    @FXML
    public void initialize() {
        Platform.runLater(() -> root.requestFocus());

        this.root.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.ENTER) {
                delete();
                event.consume();
            }
            else if (keyCode == KeyCode.ESCAPE) {
                cancel();
                event.consume();
            }
        });
    }

    /**
     * Gets the tag.
     *
     * @return the tag.
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Sets the tag.
     *
     * @param tag is the tag.
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * Sends a request to delete this tag.
     */
    public void delete() {
        try {
            server.delete(tag);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("This tag does not exist on the server!");
            alert.showAndWait();
        }
        mainCtrl.hideSecondPopup();
    }

    /**
     * Cancels the action.
     */
    public void cancel() {
        mainCtrl.hideSecondPopup();
    }
}
