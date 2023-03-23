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

    /**
     * Constructor to be called by {@link com.google.inject.Injector}.
     * @param server the {@link ServerUtils} instance to use
     * @param mainCtrl reference to the {@link MainCtrl} instance
     */
    @Inject
    public CreateNewBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Cancel the CRUD operation. Triggered by UI.
     */
    public void cancel() {
        mainCtrl.cancelCreateBoard();
    }

    /**
     * Finalise the CRUD operation. Triggered by UI.
     */
    public void add() {
        Board board = new Board(text.getText(), "");
        this.board = server.addBoard(board);
        mainCtrl.cancelCreateBoard();
    }

    /**
     * Get the {@link Board} created by finalisation of this CRUD operation. May be null.
     * @return the created {@link Board}
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Reset this CRUD controller.
     */
    public void reset() {
        this.board = null;
        text.setText("");
    }
}
