package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class BoardDisplayWorkspace implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private BoardCtrl boardCtrl;
    @FXML
    private Label label;
    @FXML
    private VBox root;

    /**
     * Creates a new {@link BoardDisplayWorkspace} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public BoardDisplayWorkspace(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Initializes the object.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Sets the BoardCtrl.
     *
     * @param boardCtrl is the BoardCtrl.
     */
    public void setBoardCtrl(BoardCtrl boardCtrl) {
        this.boardCtrl = boardCtrl;
        label.setText(boardCtrl.getBoard().name + " (" + boardCtrl.getBoard().id + ")");
    }

    /**
     * Gets the root element.
     *
     * @return the root.
     */
    public Parent getRoot() {
        return this.root;
    }

    /**
     * Displays the Board associated with this object.
     */
    public void view() {
        mainCtrl.switchBoard(boardCtrl);
    }

    /**
     * Removes this object from the workspace.
     */
    public void remove() {
        mainCtrl.removeFromWorkspace(this);
    }

    /**
     * Deletes the Board associated with this object.
     */
    public void delete() {
        mainCtrl.deleteBoard(this.boardCtrl.getBoard());
    }

    public BoardCtrl getBoardCtrl() {
        return  this.boardCtrl;
    }

    /**
     * Edits the Board associated with this object.
     */
    public void edit() {
        mainCtrl.editBoard(this.boardCtrl.getBoard());
    }

    /**
     * Refreshes this Object.
     */
    public void refresh() {
        label.setText(boardCtrl.getBoard().name + " (" + boardCtrl.getBoard().id + ")");
    }

}
