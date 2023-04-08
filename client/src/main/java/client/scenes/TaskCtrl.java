package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Tag;
import commons.Task;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class TaskCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Task task;
    @FXML
    private VBox root;
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
    @SuppressWarnings("checkstyle:MethodLength")
    @FXML
    private void initialize() {

        // event handlers to make the HBox highlight itself when the mouse hovers above it
        this.root.setOnMouseEntered(e -> {
            mainCtrl.resetFocus();

            this.root.setStyle("-fx-border-color: red; " + this.getBackgroundStyle());
            root.requestFocus();
            mainCtrl.setIsFocused(task);

            task.focused = true;
        });

        // key event handler to the root node that only works when the HBox is focused
        this.root.setOnKeyPressed(event -> {
            if (task.focused) {
                KeyCode keyCode = event.getCode();
                if (keyCode == KeyCode.E || keyCode == KeyCode.C) {
                    edit();
                    event.consume();
                }
                else if (keyCode == KeyCode.BACK_SPACE || keyCode == KeyCode.DELETE) {
                    delete();
                    event.consume();
                }
                else if (keyCode == KeyCode.ENTER) {
                    openTaskOverview();
                    event.consume();
                }
                else if (keyCode == KeyCode.T) {
                    tagOverview();
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
                else if (keyCode == KeyCode.DOWN) {
                    mainCtrl.resetFocus();
                    mainCtrl.getNextIndex(task.getTaskList(), task.index + 1);
                    event.consume();
                }
                else if (keyCode == KeyCode.UP) {
                    mainCtrl.resetFocus();
                    mainCtrl.getNextIndex(task.getTaskList(), task.index - 1);
                    event.consume();
                }
                else if (keyCode == KeyCode.LEFT) {
                    mainCtrl.resetFocus();
                    mainCtrl.getNeighbourIndex(task.getTaskList(), task.index, false);
                    event.consume();
                }
                else if (keyCode == KeyCode.RIGHT) {
                    mainCtrl.resetFocus();
                    mainCtrl.getNeighbourIndex(task.getTaskList(), task.index, true);
                    event.consume();
                }
            }
        });

    }

    private void openTaskOverview() {
        mainCtrl.openTask(task);
    }

    /**
     * Requests focus.
     */
    public void requestFocus() {
        root.requestFocus();
        task.focused = true;
        mainCtrl.setIsFocused(task);
        this.root.setStyle("-fx-border-color: red; " + this.getBackgroundStyle());
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
     * Switches the scene to Edit Task.
     */
    public void tagOverview() {
        mainCtrl.tagOverview(task.getTaskList().board);
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
    public VBox getRoot() {
        return root;
    }

    /**
     * Get the css for the task background color.
     * @return a {@link String} containing the CSS for this task background.
     */
    public String getBackgroundStyle() {
        if(!this.task.color.equals("")) {
            return "-fx-background-color: " + this.task.color + ";";
        }
        return "-fx-background-color: #f4f4f4;";
    }

    /**
     * Get the css for the task font color.
     * @return a {@link String} containing the CSS for this task font color.
     */
    public String getFontStyle() {
        if(!this.task.color.equals("")) {
            Color c = Color.valueOf(this.task.color);
            String textColor = "#000000";
            // Magic text contrast function
            if((c.getRed()*255.0*0.299 + c.getGreen()*255.0*0.587 + c.getBlue()*255.0*0.114) < 160)
                textColor = "#ffffff";
            return "-fx-text-fill: " + textColor + ";";
        }
        return "-fx-text-fill: #000000";
    }

    /**
     * Re-render the task view UI.
     */
    public void refresh() {
        this.nameLabel.setText(this.task.name);
        this.nameLabel.setStyle(this.getFontStyle());

        this.resetFocus();

        populateTags();
    }

    /**
     * Adds the visual representation of the tags.
     */
    public void populateTags() {
        for(Tag tag : task.tags) {
            Rectangle tagRepr = new Rectangle(100, 5);
            Paint paint = Paint.valueOf(tag.color);
            tagRepr.setFill(paint);
            root.getChildren().add(tagRepr);
        }
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
        VBox sourceNode = (VBox) event.getSource();

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
     * Resets focus.
     * Background resets to transparent.
     */
    public void resetFocus() {
        root.setStyle("-fx-border-color: #a8a8a8; " + this.getBackgroundStyle());
        task.focused = false;
    }

    /**
     * Open a task's extra details like `description` and `subtasks`.
     *
     * @param event the mouse event that caused this function to be called.
     */
    public void open(MouseEvent event) {
        if (!event.getButton().equals(MouseButton.PRIMARY)) {
            return;
        }

        if (event.getClickCount() != 2) {
            return;
        }

        mainCtrl.openTask(task);
    }
}
