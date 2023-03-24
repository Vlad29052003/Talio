package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Task;
import commons.TaskList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

public class TaskListCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private TaskList taskList;
    private ArrayList<TaskCtrl> taskControllers = new ArrayList<>();
    @FXML
    private TitledPane title;
    @FXML
    private VBox taskContainer;

    /**
     * Creates a new {@link TaskListCtrl} object.
     *
     * @param server is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public TaskListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Set the {@link TaskList} to be rendered by this {@link TaskListCtrl}.
     * @param taskList the {@link TaskList} to be rendered
     */
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Get the {@link TaskList} being rendered by this {@link TaskListCtrl}.
     * @return the {@link TaskList} being rendered
     */
    public TaskList getTaskList() {
        return this.taskList;
    }

    /**
     * Re-render the task list view UI.
     * This will refresh all tasks within the list.
     */
    public void refresh() {
        this.title.setText(this.taskList.name);

        this.taskContainer.getChildren().clear();
        this.taskControllers.clear();

        Set<Task> tasks = this.taskList.tasks;
        Iterator<Task> it = tasks.stream().sorted(Comparator.comparingInt(o -> o.index)).iterator();
        while(it.hasNext()) {
            Task task = it.next();

            Pair<TaskCtrl, Parent> p = mainCtrl.newTaskView(task);

            TaskCtrl controller = p.getKey();
            controller.refresh();

            this.taskContainer.getChildren().add(p.getValue());
            this.taskControllers.add(controller);
        }
    }
}
