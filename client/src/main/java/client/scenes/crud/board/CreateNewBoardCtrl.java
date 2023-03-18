package client.scenes.crud.board;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

public class CreateNewBoardCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    TextField text;

    /**
     * Creates a new {@link CreateNewBoardCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public CreateNewBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
     * Bound to the Add button.
     * Creates a new Board.
     */
    public void add() {
        Board newBoard = new Board(text.getText(), "");
        try {
            this.board = server.addBoard(newBoard);
            mainCtrl.addBoardToWorkspace(board);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        mainCtrl.addBoardToWorkspace(board);
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
