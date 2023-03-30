package client.scenes.crud.tasklists;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.TaskList;
/**import jakarta.ws.rs.WebApplicationException;
import javafx.scene.control.Alert;
import javafx.stage.Modality;**/

public class DeleteTaskListCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private TaskList taskList;

    /**
     * Creates a new {@link DeleteTaskListCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public DeleteTaskListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Sets the taskList.
     *
     * @param taskList is the TaskList.
     */
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Gets the taskList.
     *
     * @return the taskList.
     */
    public TaskList getTaskList() {
        return taskList;
    }

    /**
     * Bond to the Cancel button.
     * Switches back to the workspace Scene.
     */
    public void cancel() {
        mainCtrl.cancel();
    }

    /**
     * Bound to the Delete button.
     * Sends a request to the server
     * to delete this taskList.
     */
    public void confirm() {
        // the server part is yet to be implemented
        taskList.getBoard().removeTaskList(taskList);
        mainCtrl.getBoardCtrl().refresh();
        mainCtrl.cancel();
    }

    /**
     * Resets the fields in this object.
     */
    public void reset() {
        this.taskList = null;
    }
}
