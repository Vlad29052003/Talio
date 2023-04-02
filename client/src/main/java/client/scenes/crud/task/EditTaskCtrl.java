package client.scenes.crud.task;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Tag;
import commons.Task;
import jakarta.ws.rs.WebApplicationException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
    @FXML
    VBox tagContainer;

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
        Platform.runLater(() -> name.requestFocus());
        refresh();
    }

    /**
     * Sets the task without refreshing the text fields.
     * Used to add the tags.
     *
     * @param task is the task with tags.
     */
    public void setTaskWithoutRefreshing(Task task) {
        this.task = task;
    }

    /**
     * Initiates the edit operation.
     */
    public void edit() {
        if (name.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The name cannot be empty!\r");
            alert.showAndWait();
            return;
        }
        try {
            task.name = name.getText();
            task.description = description.getText();
            server.updateTask(task);
        } catch (WebApplicationException e) {
            e.printStackTrace();
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The task was not found on the server!");
            alert.showAndWait();
            mainCtrl.cancel();
            this.refresh();
            return;
        }
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }

    /**
     * Cancels the action.
     */
    public void cancel() {
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }

    /**
     * Refreshes the scene.
     */
    public void refresh() {
        name.setText(task.name);
        description.setText(task.description);
        tagContainer.getChildren().clear();
        for(Tag tag : task.getTaskList().board.tags) {
            var pair = mainCtrl.newAddTagListingView(tag, task);
            tagContainer.getChildren().add(pair.getValue());
        }
    }
}
