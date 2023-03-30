package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TaskCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Task task;
    @FXML
    private Label nameLabel;

    /**
     * Creates a new {@link TaskCtrl} object.
     *
     * @param server is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public TaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Set the {@link Task} to be rendered by this {@link TaskCtrl}.
     * @param task the {@link Task} to be rendered
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Get the {@link Task} being rendered by this {@link TaskCtrl}.
     * @return the {@link Task} being rendered
     */
    public Task getTask() {
        return this.task;
    }

    /**
     * Re-render the task view UI.
     */
    public void refresh() {
        this.nameLabel.setText(this.task.name);
    }

}
