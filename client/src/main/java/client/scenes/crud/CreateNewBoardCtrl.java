package client.scenes.crud;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateNewBoardCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    TextField text;

    @Inject
    public CreateNewBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void cancel() {
        mainCtrl.cancel();
    }

    public void add() {
        Board board = new Board(text.getText(), "");
        this.board = server.addBoard(board);
        mainCtrl.cancel();
    }

    public Board getBoard() {
        return board;
    }

    public void reset() {
        this.board = null;
        text.setText("");
    }
}
