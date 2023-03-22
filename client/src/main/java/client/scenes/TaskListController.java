package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.TaskList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class TaskListController {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private TaskList tasklist;
    @FXML
    private Label label;
    @FXML
    private VBox root;
    @FXML
    private VBox container;

    /**
     * Creates a new {@link TaskListController} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public TaskListController(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Sets the TaskList.
     *
     * @param tasklist is the TaskList.
     */
    public void setTasklist(TaskList tasklist) {
        this.tasklist = tasklist;
        label.setText(tasklist.name);
    }

    /**
     * Gets the root element.
     *
     * @return the root.
     */
    public Parent getRoot() {
        return this.root;
    }

    /**
     * Sets the root element.
     *
     * @param root is the root
     */
    public void setRoot(VBox root) {
        this.root = root;
    }

    /**
     * Deletes the Board associated with this object.
     */
    public void delete() {

    }

    /**
     * Edits the Board associated with this object.
     */
    public void edit() {

    }

    /**
     * Edits the Board associated with this object.
     */
    public void addTask() {}

    /**
     * Refreshes this Object.
     */
    public void refresh() {
        label.setText(tasklist.name);
    }

}
