package client.scenes.crud.task;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Task;
import jakarta.ws.rs.WebApplicationException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javax.inject.Inject;

public class DeleteTaskCtrl {
    private MainCtrl mainCtrl;
    private ServerUtils server;
    private Task task;
    @FXML
    AnchorPane root;

    /**
     * Creates a new {@link DeleteTaskCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public DeleteTaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
                delete();
                event.consume();
            }
            else if (keyCode == KeyCode.ESCAPE) {
                cancel();
                event.consume();
            }
        });
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
    }

    /**
     * Starts the delete operation.
     */
    public void delete() {
        try {
            server.delete(task);
            mainCtrl.removeTask(task);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("This board does not exist on the server!");
            alert.showAndWait();
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
}
