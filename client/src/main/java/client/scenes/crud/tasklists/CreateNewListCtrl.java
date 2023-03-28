package client.scenes.crud.tasklists;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.TaskList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

    public Board getBoard() {
        return board;
    }

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
        TaskList newTaskList = new TaskList(text.getText(), 1);
        board.addTaskList(newTaskList);
        // server part to be implemented
        mainCtrl.addTaskListToBoard(newTaskList);
        mainCtrl.cancel();
    }

    /**
     * Resets the fields in this object.
     */
    public void reset() {
        text.setText("");
    }
}