package client.scenes.crud.tasklists;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.TaskList;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

/**import jakarta.ws.rs.WebApplicationException;
import javafx.scene.control.Alert;
import javafx.stage.Modality;**/

public class CreateNewListCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    TextField text;

    /**
     * Creates a new {@link CreateNewListCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public CreateNewListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Gets the Board.
     *
     * @return Board to be returned.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the Board.
     *
     * @param board is the Board to be set.
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
     * Bound to the Add button.
     * Creates a new TaskList.
     */
    public void add() {
        try {
            board = server.addTaskList(new TaskList(text.getText()), board.id);
            System.out.println(board);
            mainCtrl.refreshBoard(board);
            reset();
            mainCtrl.cancel();
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There has been an error!\r" + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Resets the fields in this object.
     */
    public void reset() {
        text.setText("");
    }
}