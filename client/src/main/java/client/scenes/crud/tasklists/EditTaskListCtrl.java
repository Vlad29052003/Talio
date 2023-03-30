package client.scenes.crud.tasklists;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.TaskList;
import jakarta.ws.rs.WebApplicationException;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.stage.Modality;

/**import javafx.scene.control.Alert;
import jakarta.ws.rs.WebApplicationException;
import javafx.stage.Modality;**/

public class EditTaskListCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private TaskList taskList;
    @FXML
    private TextField text;

    /**
     * Creates a new {@link EditTaskListCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public EditTaskListCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
     * Bound to the Cancel button.
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
        if(text.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There name cannot be empty!\r");
            alert.showAndWait();
            return;
        }
        try {
            this.taskList.name = text.getText();
            this.taskList = server.editTaskList(taskList);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The list was not found on the server!" +
                    "\rIt will be removed from the workspace!");
            alert.showAndWait();
            mainCtrl.cancel();
            this.reset();
            return;
        }

        mainCtrl.updateTaskList(taskList);
        mainCtrl.cancel();
    }

    /**
     * Resets the fields in this object.
     */
    public void reset() {
        this.taskList = null;
        text.setText("");
    }
}
