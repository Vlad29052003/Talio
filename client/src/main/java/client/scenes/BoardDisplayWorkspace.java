package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class BoardDisplayWorkspace{
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
     * Gets the label.
     *
     * @return the Label.
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Sets the label.
     *
     * @param label is the Label.
     */
    public void setLabel(Label label) {
        this.label = label;
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
     * Sets the root element.
     *
     * @param root is the root
     */
    public void setRoot(VBox root) {
        this.root = root;
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
