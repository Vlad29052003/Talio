package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Task;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Modality;

public class TaskCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Task task;
    @FXML
    private HBox root;
    @FXML
    private Label nameLabel;
    @FXML
    private ListView<String> subTaskList;

    private static final int LIST_CELL_HEIGHT = 30;

    /**
     * Creates a new {@link TaskCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public TaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Initializes the TaskCtrl FXML controller.
     */
    @FXML
    private void initialize() {

        // event handlers to make the HBox highlight itself when the mouse hovers above it
        this.root.setOnMouseEntered(e -> {
            this.root.setStyle("-fx-background-color: lightgray;");
            root.requestFocus();
            mainCtrl.setIsFocused(task);

            Board currentBoard = task.getTaskList().board;
            currentBoard.lists.stream().flatMap(l -> l.tasks.stream()).
                    filter(t -> t.focused = true).map(t -> t.focused = false);
            task.focused = true;
        });

//        this.root.setOnMouseExited(e -> {
//            this.root.setStyle("-fx-background-color: transparent;");
//        });

        // key event handler to the root node that only works when the HBox is focused
        this.root.setOnKeyPressed(event -> {
            if (task.focused) {
                KeyCode keyCode = event.getCode();
                if (keyCode == KeyCode.E) {
                    edit();
                    event.consume();
                }
                else if (keyCode == KeyCode.BACK_SPACE || keyCode == KeyCode.DELETE) {
                    delete();
                    event.consume();
                }
                else if (keyCode == KeyCode.DOWN && event.isShiftDown()) {
                    int index = task.index;
                    index++;

                    if (index >= 0 && index < task.getTaskList().tasks.size()){
                        sendMoveRequest(index);
                    }

                    event.consume();
                }
                else if (keyCode == KeyCode.UP && event.isShiftDown()) {
                    int index = task.index;
                    index--;

                    if (index >= 0 && index < task.getTaskList().tasks.size()){
                        sendMoveRequest(index);
                    }

                    event.consume();
                }
            }
        });

    }

    public void requestFocus(){
        root.requestFocus();
        task.focused = true;
    }

    /**
     * Sends a request to the server to update the list
     * and the index of the moved task.
     *
     * @param newIndex is the newIndex within the TaskList.
     */
    public void sendMoveRequest(int newIndex) {
        try {
            server.dragAndDrop(task.getTaskList().id, newIndex, task.id);
        } catch (Exception e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There has been an error!\r" + e.getMessage());
            alert.showAndWait();
            refresh();
        }
    }

    /**
     * Switches the scene to Edit Task.
     */
    public void edit() {
        mainCtrl.editTask(task);
    }

    /**
     * Switches the scene to Delete Task.
     */
    public void delete() {
        mainCtrl.deleteTask(task);
    }

    /**
     * Set the {@link Task} to be rendered by this {@link TaskCtrl}.
     *
     * @param task the {@link Task} to be rendered
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Get the {@link Task} being rendered by this {@link TaskCtrl}.
     *
     * @return the {@link Task} being rendered
     */
    public Task getTask() {
        return this.task;
    }

    /**
     * Gets the root element.
     *
     * @return the root.
     */
    public HBox getRoot() {
        return root;
    }

    /**
     * Re-render the task view UI.
     */
    public void refresh() {
        this.nameLabel.setText(this.task.name);
    }

    /**
     * Specifies behaviour on drag detected.
     * Makes the node draggable, adds an image to the mouse pointer
     * to indicate the node that is being dragged and sets the task and
     * the root element of the dragged node in order to be recognizable.
     *
     * @param event is the mouse event.
     */
    public void onDragDetected(MouseEvent event) {
        HBox sourceNode = (HBox) event.getSource();

        Dragboard db = sourceNode.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(String.valueOf(task.id));
        db.setContent(content);
        mainCtrl.setDragAndDropNode(sourceNode);

        WritableImage image = sourceNode.snapshot(new SnapshotParameters(), null);
        db.setDragView(image, image.getWidth() / 2, image.getHeight() / 2);

        event.consume();
    }


}
