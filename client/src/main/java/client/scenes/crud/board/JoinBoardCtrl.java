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

public class JoinBoardCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    private TextField text;

    /**
     * Creates a new {@link JoinBoardCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public JoinBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
     * Gets the entered id.
     *
     * @return the id.
     */
    public Long getID() throws Exception {
        String idText = text.getText();
        long id = 0L;
        id = Long.parseLong(idText);
        return id;
    }

    /**
     * Bond to the Cancel button.
     * Switches back to the workspace Scene.
     */
    public void cancel() {
        mainCtrl.cancel();
    }

    /**
     * Bound to the Join button.
     * Sends a request to the server
     * to get the Board with the entered id.
     */
    public void confirm() {
        long id;
        try {
            id = getID();
        } catch (Exception e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Invalid ID. It must be numeric.\rPlease try again!");
            alert.showAndWait();
            this.reset();
            return;
        }
        try {
            board = server.joinBoard(id);

            if (mainCtrl.isPresent(board)) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setContentText("The board is already present in the workspace!");
                alert.showAndWait();
                this.reset();
                return;
            }

            mainCtrl.addBoardToWorkspace(board);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There is no board with this id.\rPlease try again!");
            alert.showAndWait();
            this.reset();
            return;
        }
        mainCtrl.cancel();
    }

    /**
     * Resets the fields in this object.
     */
    public void reset() {
        text.setText("");
        this.board = null;
    }
}
