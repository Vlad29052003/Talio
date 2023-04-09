package client.scenes.crud.admin;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class EditBoardPasswordCtrl {

    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    private TextField text;

    /**
     * Creates a new {@link EditBoardPasswordCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public EditBoardPasswordCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
        text.setText(board.password);
    }

    /**
     * Bond to the Cancel button.
     * Switches back to the workspace Scene.
     */
    public void cancel() {
        Platform.runLater(() -> text.requestFocus());
        reset();
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }

    /**
     * Bound to the Confirm button.
     * Sends a request to the server
     * to replace the password.
     */
    public void confirm(){
        Platform.runLater(() -> text.requestFocus());
        if(board != null){
            if (text.getText() == "") {
                board.password = text.getText();
                board.editable = true;
                mainCtrl.hidePopup();
                reset();
            } else {
                board.password = text.getText();
                board.editable = false;
                mainCtrl.accessDenied();
                mainCtrl.hidePopup();
                reset();
            }
            server.updateBoard(board);
            mainCtrl.updateBoard(board);
        }else {
            mainCtrl.cancel();
            mainCtrl.hidePopup();
        }
    }

    /**
     * Getter for the Board;
     *
     * @return the current board;
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Resets the fields in this object.
     */
    private void reset() {
        text.setText("");
    }
}
