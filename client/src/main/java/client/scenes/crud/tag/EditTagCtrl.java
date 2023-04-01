package client.scenes.crud.tag;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Tag;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javax.inject.Inject;

public class EditTagCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Tag tag;
    @FXML
    private TextField name;

    /**
     * Creates a new {@link EditTagCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public EditTagCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
        name.setText(tag.name);
    }

    /**
     * Sends a update to delete this tag.
     */
    public void edit() {
        if (name.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The name cannot be empty!\r");
            alert.showAndWait();
            return;
        }
        try {
            tag.name = name.getText();
            Tag edited = server.updateTag(tag);
            mainCtrl.updateTag(tag, edited);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The tag was not found on the server!");
            alert.showAndWait();
            mainCtrl.cancel();
            this.reset();
            return;
        }
        mainCtrl.hideSecondPopup();
    }

    /**
     * Cancels the action.
     */
    public void cancel() {
        mainCtrl.hideSecondPopup();
    }

    /**
     * Resets the text.
     */
    private void reset() {
        name.setText("");
    }
}
