package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class BoardDisplayWorkspace{
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Board board;
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
     * Sets the Board.
     *
     * @param board is the Board.
     */
    public void setBoard(Board board) {
        this.board = board;
        label.setText(board.name + " (" + board.id + ")");
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
        mainCtrl.switchBoard(board);
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
        mainCtrl.deleteBoard(this.board);
    }

    public Board getBoard() {
        return  this.board;
    }

    /**
     * Edits the Board associated with this object.
     */
    public void edit() {
        mainCtrl.editBoard(this.board);
    }

    /**
     * Refreshes this Object.
     */
    public void refresh() {
        label.setText(board.name + " (" + board.id + ")");
    }

}
