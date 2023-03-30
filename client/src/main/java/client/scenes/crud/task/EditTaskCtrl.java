package client.scenes.crud.task;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Task;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javax.inject.Inject;

public class EditTaskCtrl {
    private MainCtrl mainCtrl;
    private ServerUtils server;
    private Task task;
    @FXML
    TextField name;
    @FXML
    TextArea description;

    /**
     * Creates a new {@link EditTaskCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public EditTaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Gets the task.
     *
     * @return the task.
     */
    public Task getTask() {
        return task;
    }

    /**
     * Sets the task.
     *
     * @param task is the task.
     */
    public void setTask(Task task) {
        this.task = task;
        refresh();
    }

    /**
     * Initiates the edit operation.
     */
    public void edit() {
        if (name.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There name cannot be empty!\r");
            alert.showAndWait();
            return;
        }
        try {
            task.name = name.getText();
            task.description = description.getText();
            server.updateTask(task);
            mainCtrl.updateTaskInList(task);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The board was not found on the server!" +
                    "\rIt will be removed from the workspace!");
            alert.showAndWait();
            mainCtrl.cancel();
            this.refresh();
            return;
        }
        mainCtrl.cancel();
    }

    /**
     * Cancels the action.
     */
    public void cancel() {
        mainCtrl.cancel();
    }

    /**
     * Refreshes the scene.
     */
    public void refresh() {
        name.setText(task.name);
        description.setText(task.description);
    }
}
