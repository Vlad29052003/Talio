package client.scenes.crud.tasklists;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.TaskList;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
/**import javafx.scene.control.Alert;
import jakarta.ws.rs.WebApplicationException;
import javafx.stage.Modality;**/

public class EditListCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private TaskList taskList;
    @FXML
    private TextField text;

    /**
     * Creates a new {@link EditListCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public EditListCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
        this.text.setText(taskList.name);
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
     * Bound to the Confirm button.
     * Sends a request to the server
     * to update this taskList.
     */
    public void edit() {
        // to be implemented
    }

    /**
     * Resets the fields in this object.
     */
    public void reset() {
        this.taskList = null;
        text.setText("");
    }
}
