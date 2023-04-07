package client.scenes.crud.task;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Task;
import commons.TaskList;
import jakarta.ws.rs.WebApplicationException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javax.inject.Inject;

public class CreateTaskCtrl {
    private MainCtrl mainCtrl;
    private ServerUtils server;
    private TaskList taskList;
    @FXML
    TextField name;
    @FXML
    TextArea description;

    /**
     * Creates a new {@link CreateTaskCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public CreateTaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Gets the TaskList.
     *
     * @return the TaskList.
     */
    public TaskList getTaskList() {
        return taskList;
    }

    /**
     * Sets the taskList.
     *
     * @param taskList is the TaskList.
     */
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
        Platform.runLater(() -> name.requestFocus());
    }

    /**
     * Saves the Task on the server.
     */
    public void add() {
        if (name.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The name cannot be empty!\r");
            alert.showAndWait();
            return;
        }
        try {
            Task created = new Task(name.getText(), taskList.tasks.size(), description.getText());
            created = server.addTask(created, taskList.id);
            taskList.tasks.add(created);

            mainCtrl.updateTaskList(taskList);
            refresh();
            mainCtrl.cancel();
            mainCtrl.hidePopup();
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There has been an error!\r" + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Cancels the action.
     */
    public void cancel() {
        refresh();
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }

    /**
     * Refreshes the text.
     */
    public void refresh() {
        name.setText("");
        description.setText("");
    }

}
