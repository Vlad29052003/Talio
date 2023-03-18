package client.scenes.crud.board;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditBoardCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    private TextField text;

    /**
     * Creates a new {@link EditBoardCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public EditBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
        this.text.setText(board.name);
    }

    /**
     * Gets the board.
     *
     * @return the board.
     */
    public Board getBoard() {
        return board;
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
     * to update this board.
     */
    public void confirm() {
        this.board.name = text.getText();
        this.board = server.updateBoard(board);
        mainCtrl.updateBoard(board);
        mainCtrl.cancel();
    }

    /**
     * Resets the fields in this object.
     */
    public void reset() {
        this.board = null;
        text.setText("");
    }
}
