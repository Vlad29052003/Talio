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

public class CreateTaskListCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    TextField text;

    /**
     * Creates a new {@link CreateTaskListCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public CreateTaskListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Return the board on which this CRUD operation is currently creating a list.
     * @return the relative {@link Board}
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Set the board on which this CRUD operation will be creating a list
     * @param board the relative {@link Board}
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
        TaskList newTaskList = new TaskList(text.getText());
        board.addTaskList(newTaskList);
        // server part to be implemented
        mainCtrl.getBoardCtrl().refresh();
        mainCtrl.cancel();
    }

    /**
     * Resets the fields in this object.
     */
    public void reset() {
        text.setText("");
    }
}