package client.scenes.crud.board;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditPermissionCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    private TextField text;

    /**
     * Creates a new {@link EditPermissionCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public EditPermissionCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Sets the board.
     *
     * @param board is the Board.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Bond to the Cancel button.
     * Switches back to the workspace Scene.
     */
    public void cancel() {
        mainCtrl.cancel();
    }

    /**
     * Bound to the Confirm button.
     * Sends a request to the server
     * to test the password.
     */
    public void confirm(){
        if(board.password.equals(text.getText())){
            board.edit = true;
            mainCtrl.switchBoard(board);
        }else{
            reset();
            mainCtrl.accessDenied();
        }
    }

    /**
     * Resets the fields in this object.
     */
    public void reset() {
        text.setText("");
        this.board = null;
    }

    /**
     * Getter for the selected board.
     *
     * @return board
     */
    public Board getBoard() {
        return board;
    }
}
