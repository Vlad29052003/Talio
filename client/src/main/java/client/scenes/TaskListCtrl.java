package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Task;
import commons.TaskList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TaskListCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private TaskList taskList;
    private ArrayList<TaskCtrl> taskControllers = new ArrayList<>();
    @FXML
    private Label title;
    @FXML
    private VBox root;
    @FXML
    private VBox taskContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox header;
    private Region placeholder;

    /**
     * Creates a new {@link TaskListCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public TaskListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        placeholder = new Region();
        placeholder.setStyle("-fx-background-color: rgba(79,75,75,0.5);");
    }

    /**
     * Getter for TaskControllers.
     *
     * @return ArrayList
     */
    public ArrayList<TaskCtrl> getTaskControllers() {
        return taskControllers;
    }

    /**
     * Getter for root.
     *
     * @return root field
     */
    public VBox getRoot() {
        return root;
    }

    /**
     * Set the {@link TaskList} to be rendered by this {@link TaskListCtrl}.
     *
     * @param taskList the {@link TaskList} to be rendered
     */
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Get the {@link TaskList} being rendered by this {@link TaskListCtrl}.
     *
     * @return the {@link TaskList} being rendered
     */
    public TaskList getTaskList() {
        return this.taskList;
    }

    /**
     * Deletes the TaskList associated with this object.
     */
    public void delete() {
        if(mainCtrl.getAdmin() || taskList.board.isEditable()) {
            mainCtrl.deleteTaskList(this.taskList);
        }else{
            mainCtrl.unlockBoard(taskList.board);
        }
    }

    /**
     * Edits the TaskList associated with this object.
     */
    public void edit() {
        if(mainCtrl.getAdmin() || taskList.board.isEditable()) {
            mainCtrl.editTaskList(this.taskList);
        }else{
            mainCtrl.unlockBoard(taskList.board);
        }
    }

    /**
     * Adds a task to this taskList.
     */
    public void addTask() {
        if(mainCtrl.getAdmin() || taskList.board.isEditable()) {
            mainCtrl.addTask(this.taskList);
        }else{
            mainCtrl.unlockBoard(taskList.board);
        }
    }

    /**
     * Re-render the task list view UI.
     * This will refresh all tasks within the list.
     */
    public void refresh() {
        this.title.setText(this.taskList.name);

        taskContainer.minHeightProperty().bind(scrollPane.heightProperty());

        this.taskContainer.setStyle("-fx-background-color: #f4f4f4;");
        this.root.setStyle("-fx-background-color: #f4f4f4;");
        this.header.setStyle("-fx-border-color: #a8a8a8");
        this.title.setTextFill(Paint.valueOf("#000000"));

        Board board = taskList.board;

        if (!board.listBackgroundColor.equals("")) {
            this.taskContainer.setStyle("-fx-background-color: " + board.listBackgroundColor);
            this.header.setStyle("-fx-background-color: " + board.listBackgroundColor +
                    "; -fx-background-radius: 15 15 0 0;" +
                    "-fx-border-color: " + board.listFontColor + ";");
            this.root.setStyle("-fx-border-color: " + board.listFontColor + ";");
        }
        if (!board.listFontColor.equals("")) {
            this.title.setStyle("-fx-text-fill: " + board.listFontColor + ";");
        }

        this.taskContainer.getChildren().clear();
        this.taskControllers.clear();

        taskList.sort();
        List<Task> tasks = this.taskList.tasks;
        for (Task task : tasks) {
            Pair<TaskCtrl, Parent> p = mainCtrl.newTaskView(task);

            TaskCtrl controller = p.getKey();
            controller.refresh();

            this.taskContainer.getChildren().add(p.getValue());
            this.taskControllers.add(controller);

            if (mainCtrl.getIsFocused() != null && task.id == mainCtrl.getIsFocused().id) {
                Platform.runLater(() -> {
                    try {
                        Thread.sleep(75);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    p.getKey().requestFocus();
                });
            }
        }

    }

    /**
     * Specifies behaviour on drag over.
     * Calculates the position the node would be inserted at and
     * places a placeholder for visually indicating the position.
     *
     * @param event is the drag event.
     */
    public void onDragOver(DragEvent event) {
        if (placeholder != null) {
            taskContainer.getChildren().remove(placeholder);
        }

        placeholder.setPrefSize(0, ((VBox) event.getGestureSource()).getHeight());

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
        }

        event.consume();
    }

    /**
     * Removes the placeholder when the drag and drop
     * movement exits this TaskList.
     */
    public void handleDragLeave() {
        taskContainer.getChildren().remove(placeholder);
    }

    /**
     * Specifies behaviour on drag dropped.
     * The placeholder is replaced by the actual node.
     *
     * @param event is the drag event.
     */
    public void onDragDropped(DragEvent event) {
        VBox source = (VBox) mainCtrl.getDragAndDropNode();
        Dragboard db = event.getDragboard();

        if (source.getParent() == taskContainer) taskContainer.getChildren().remove(source);
        if (placeholder != null) {
            int index = taskContainer.getChildren().indexOf(placeholder);
            sendMoveRequest(Long.parseLong(db.getString()), index);
            taskContainer.getChildren().set(index, source);
            taskContainer.getChildren().remove(placeholder);
        }
        event.setDropCompleted(true);
        event.consume();
    }

    /**
     * Sends a request to the server to update the list
     * and the index of the moved task.
     *
     * @param taskId   is the id of the moved Task.
     * @param newIndex is the newIndex within the TaskList.
     */
    public void sendMoveRequest(long taskId, int newIndex) {
        try {
            server.dragAndDrop(taskList.id, newIndex, taskId);
        } catch (Exception e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There has been an error!\r" + e.getMessage());
            alert.showAndWait();
            refresh();
        }
    }

    /**
     * Gets the index of the following task.
     *
     * @param index of the Task.
     */
    public void getNextIndex(int index) {
        if (index < 0) {
            index = taskList.tasks.size() - 1;
        }
        if (index >= taskList.tasks.size()) {
            index = 0;
        }

        Task task = taskList.tasks.get(index);
        taskControllers.stream().filter(tc -> tc.getTask().id == task.id)
                .forEach(TaskCtrl::requestFocus);
    }

    /**
     * Gets the index of the neighbouring task.
     *
     * @param index of the Task.
     */
    public void getNeighbour(int index) {
        if (index >= taskList.tasks.size()) {
            index = taskList.tasks.size() - 1;
        }
        if (index < 0) {
            return;
        } else {
            Task task = taskList.tasks.get(index);
            taskControllers.stream().filter(tc -> tc.getTask().id == task.id)
                    .forEach(TaskCtrl::requestFocus);
        }

    }
}
