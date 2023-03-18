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
    TextField text;

    @Inject
    public EditBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void cancel() {
        mainCtrl.cancel();
    }

    public void confirm() {
        this.board.name = text.getText();
        this.board = server.updateBoard(board);
        mainCtrl.updateBoard(board);
        mainCtrl.cancel();
    }

    public void setBoard(Board board) {
        this.board = board;
        this.text.setText(board.name);
    }

    public Board getBoard() {
        return board;
    }

    public void reset() {
        this.board = null;
        text.setText("");
    }
}
