package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Tag;
import commons.Task;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
    public VBox getRoot() {
        return root;
    }

    /**
     * Re-render the task view UI.
     */
    public void refresh() {
        this.nameLabel.setText(this.task.name);
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


}
