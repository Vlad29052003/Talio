package client.scenes.crud.board;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class UnlockBoardCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    private TextField text;

    /**
     * Creates a new {@link UnlockBoardCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public UnlockBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Autofocuses the first field.
     * Sets the keyboard shortcuts for ENTER and ESC.
     */
    public void initialize() {
        Platform.runLater(() -> text.requestFocus());
        this.text.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.ENTER) {
                confirm();
                event.consume();
            }
            else if (keyCode == KeyCode.ESCAPE) {
                cancel();
                event.consume();
            }
        });
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
        Platform.runLater(() -> text.requestFocus());
        mainCtrl.cancel();
        mainCtrl.hidePopup();
        reset();
    }

    /**
     * Bound to the Confirm button.
     * Sends a request to the server
     * to test the password.
     */
    public void confirm(){
        Platform.runLater(() -> text.requestFocus());
        if(board.password.equals(text.getText())){
            board.editable = true;
            mainCtrl.getUnlockedBoards().add(board.id);
            mainCtrl.updateBoard(board);
            mainCtrl.switchBoard(board);
            mainCtrl.youHavePermission();
            reset();
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
