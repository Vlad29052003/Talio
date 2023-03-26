package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Task;
import commons.TaskList;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
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
    private Region placeholder;

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
        placeholder = new Region();
        placeholder.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

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

    public void onDragOver(DragEvent event) {
        if (placeholder != null) {
            taskContainer.getChildren().remove(placeholder);
        }

        placeholder.setPrefSize(0, ((HBox) event.getGestureSource()).getHeight());

        event.acceptTransferModes(TransferMode.MOVE);
        double y = event.getY();

        int index = -1;
        for (int i = 0; i < taskContainer.getChildren().size(); i++) {
            Node child = taskContainer.getChildren().get(i);
            Bounds bounds = child.getBoundsInParent();
            double childY = bounds.getMinY() + bounds.getHeight() / 2;
            if (y < childY) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            index = taskContainer.getChildren().size();
        }

        if (index >= taskContainer.getChildren().size()) {
            taskContainer.getChildren().add(placeholder);
        } else {
            taskContainer.getChildren().add(index, placeholder);
            for (int i = index + 1; i < taskContainer.getChildren().size(); i++) {
                Node node = taskContainer.getChildren().get(i);
                TranslateTransition tt = new TranslateTransition(Duration.millis(200), node);
                tt.setToY(tt.getToY() + placeholder.getBoundsInParent().getHeight());
                tt.play();
            }
        }

        event.consume();
    }

    public void handleDragLeave() {
        taskContainer.getChildren().remove(placeholder);
    }

    public void onDragDropped(DragEvent event) {
        HBox source = (HBox) mainCtrl.getDnd();
        if(source.getParent() == taskContainer)taskContainer.getChildren().remove(source);
        if (placeholder != null) {
            int index = taskContainer.getChildren().indexOf(placeholder);
            taskContainer.getChildren().set(index, source);
            taskContainer.getChildren().remove(placeholder);
        }
        event.setDropCompleted(true);
        event.consume();
    }



}
