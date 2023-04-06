package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Task;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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
    boolean isFocused = false;

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
            isFocused = true;
            root.requestFocus();
        });

        this.root.setOnMouseExited(e -> {
            this.root.setStyle("-fx-background-color: transparent;");
            isFocused = false;
        });

        // key event handler to the root node that only works when the HBox is focused
        this.root.setOnKeyPressed(event -> {
            if (isFocused && event.getCode() == KeyCode.E) {
                // call the edit() method when the "E" key is pressed
                edit();
                event.consume();
            }
        });
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
        this.subTaskList.setItems(FXCollections.observableList(this.task.subtasks));

        // make it so that the subtask list automatically resizes
        // when elements are added or removed.
        var itemListProperty = this.subTaskList.getItems();
        this.subTaskList.prefHeightProperty()
                .bind(Bindings.size(itemListProperty).multiply(LIST_CELL_HEIGHT));
        this.subTaskList.refresh();
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


    /**
     * The button to add a subtask is clicked.
     */
    public void onAddTask() {
        var input = new TextInputDialog();
        input.setTitle("Create new Subtask");
        input.showAndWait();

        var name = input.getEditor().getText();

        //TODO update this on the server
        this.task.addSubTask(name);
        this.refresh();
    }
}
