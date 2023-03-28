package client.scenes;

import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import static commons.Password.getAdmin;

public class BoardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Board board;
    @FXML
    private Button readwritepassword;
    @FXML
    private Button readwrite;
    @FXML
    private Label boardTitle;
    @FXML
    private HBox listContainer;

    /**
     * Creates a new {@link BoardCtrl} object.
     *
     * @param server is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public BoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Getter for the Board.
     *
     * @return the board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Setter for the Board.
     *
     * @param board the new Board.
     */
    public void setBoard(Board board) {
        this.board = board;
        refresh();
    }

    /**
     * Resets the name of the Board.
     */
    private void resetBoardName() {
        if(board != null) {
            boardTitle.setText(board.name + " (id: " + board.id + ")");
        }
        else boardTitle.setText("No board to be displayed");
    }

    /**
     * Re-render the board view UI.
     * This will refresh all task lists and tasks currently rendered.
     */
    public void refresh() {
        this.listContainer.getChildren().clear();
        resetBoardName();
    }

    /**
     * Checks if you have the read / write permission for the board.
     * In case you are missing it you have to input it.
     */
    public void editPermissions() {
        if(board.edit == false) {
            mainCtrl.readWritePermissions(this.board);
        }else{
            mainCtrl.youHavePermission();
        }
    }

    /**
     * Checks if the user has administrator privileges and edits the board password.
     * If not the scene is changed to the password input.
     */
    public void passwordEdit() {
        if(getAdmin() == true){
            mainCtrl.editPassword(board);
        }else{
            mainCtrl.grantAdmin();
        }
    }
}
