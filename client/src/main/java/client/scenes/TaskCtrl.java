package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaskCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Task task;
    @FXML
    private HBox root;
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

    public HBox getRoot() {
        return root;
    }

    /**
     * Re-render the task view UI.
     */
    public void refresh() {
        this.nameLabel.setText(this.task.name);
    }

    public void onDragDetected(MouseEvent event) {
        HBox sourceNode = (HBox) event.getSource();

        Dragboard db = sourceNode.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString("taskList");
        db.setContent(content);
        mainCtrl.setDnd(sourceNode);

        WritableImage image = sourceNode.snapshot(new SnapshotParameters(), null);
        db.setDragView(image, image.getWidth() / 2, image.getHeight() / 2);

        event.consume();
    }


}
