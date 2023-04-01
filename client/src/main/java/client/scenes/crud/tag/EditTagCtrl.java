package client.scenes.crud.tag;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.inject.Inject;

public class EditTagCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Tag tag;
    @FXML
    private TextField name;

    @Inject
    public EditTagCtrl (ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public void edit() {

    }

    public void cancel() {

    }
}
