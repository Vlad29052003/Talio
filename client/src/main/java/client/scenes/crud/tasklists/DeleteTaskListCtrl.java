package client.scenes.crud.tasklists;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.TaskList;
import jakarta.ws.rs.WebApplicationException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

public class DeleteTaskListCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private TaskList taskList;
    @FXML
    AnchorPane root;

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
     * Sets the keyboard shortcuts for ENTER and ESC.
     */
    @FXML
    public void initialize() {
        Platform.runLater(() -> root.requestFocus());

        this.root.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.ENTER) {
                confirm();
                event.consume();
            }
            else if (keyCode == KeyCode.ESCAPE) {
                cancel();
                event.consume();
            }
        });
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
        mainCtrl.hidePopup();
    }

    /**
     * Bound to the Delete button.
     * Sends a request to the server
     * to delete this taskList.
     */
    public void confirm() {
        try {
            server.deleteTaskList(taskList);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("This list does not exist on the server!");
            alert.showAndWait();
        }
        mainCtrl.hidePopup();
    }
}
