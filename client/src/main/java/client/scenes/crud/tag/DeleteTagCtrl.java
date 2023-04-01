package client.scenes.crud.tag;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Tag;
import jakarta.ws.rs.WebApplicationException;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javax.inject.Inject;

public class DeleteTagCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Tag tag;

    @Inject
    public DeleteTagCtrl (ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public void delete() {
        try {
            server.delete(tag);
            mainCtrl.removeTag(tag);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("This tag does not exist on the server!");
            alert.showAndWait();
        }
        mainCtrl.hideSecondPopup();
    }

    public void cancel() {
        mainCtrl.hideSecondPopup();
    }
}
