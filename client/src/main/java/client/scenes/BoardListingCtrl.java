package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class BoardListingCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ImageView locked;
    private ImageView unlocked;
    private Board board;
    @FXML
    private Label label;
    @FXML
    private VBox root;
    @FXML
    private HBox header;

    /**
     * Creates a new {@link BoardListingCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public BoardListingCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
        label.setText(board.name + " (id: " + board.id + ")");
        if(locked == null) {
            this.locked = new ImageView(new Image("/client/icons/lock.png"));
            locked.setScaleX(0.7);
            locked.setScaleY(0.7);
            this.unlocked = new ImageView(new Image("/client/icons/unlock.png"));
            unlocked.setScaleX(0.7);
            unlocked.setScaleY(0.7);
        }
        if (!board.isEditable() && !header.getChildren().contains(locked)) {
            header.getChildren().add(locked);
            header.getChildren().remove(unlocked);
        }
        else {
            header.getChildren().remove(locked);
            header.getChildren().remove(unlocked);
            if(board.password != null && board.password.length() > 0) {
                header.getChildren().add(unlocked);
            }
        }
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
        mainCtrl.switchBoard(this.board);
        if (this.board.password != null) {
            if (mainCtrl.getAdmin() || board.isEditable()) {
                mainCtrl.deleteBoard(this.board);
            } else {
                mainCtrl.unlockBoard(this.board);
            }
        } else {
            mainCtrl.deleteBoard(this.board);
        }
    }

    /**
     * Get the {@link Board} associated with this list entry.
     *
     * @return the associated {@link Board}
     */

    public Board getBoard() {
        return this.board;
    }

    /**
     * Edits the Board associated with this object.
     */
    public void edit() {
        mainCtrl.switchBoard(this.board);
        if (mainCtrl.getAdmin() || board.isEditable()) {
            mainCtrl.editBoard(this.board);
        } else {
            mainCtrl.unlockBoard(this.board);
        }
    }

    /**
     * Refreshes this Object.
     */
    public void refresh() {
        label.setText(board.name + " (id: " + board.id + ")");
    }

}
