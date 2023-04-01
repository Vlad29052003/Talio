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

    /**
     * Creates a new {@link TagListingCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public TagListingCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
        refresh();
    }

    /**
     * Switches to edit tag scene.
     */
    public void editTag() {
        mainCtrl.editTag(tag);
    }

    /**
     * Switches to delete tag scene.
     */
    public void deleteTag() {
        mainCtrl.deleteTag(tag);
    }

    /**
     * Refreshes the name.
     */
    public void refresh() {
        this.name.setText(tag.name);
    }
}
