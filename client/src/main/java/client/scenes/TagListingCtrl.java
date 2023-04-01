package client.scenes;

import client.utils.ServerUtils;
import commons.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javax.inject.Inject;

public class TagListingCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Tag tag;
    @FXML
    private Label name;

    @Inject
    public TagListingCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        refresh();
    }

    public void editTag() {

    }

    public void deleteTag() {
        mainCtrl.deleteTag(tag);
    }

    public void refresh() {
        this.name.setText(tag.name);
    }
}
