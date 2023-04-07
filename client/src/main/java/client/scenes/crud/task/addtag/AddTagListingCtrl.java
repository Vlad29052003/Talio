package client.scenes.crud.task.addtag;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.jfoenix.controls.JFXButton;
import commons.Tag;
import commons.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javax.inject.Inject;

public class AddTagListingCtrl {
    private MainCtrl mainCtrl;
    private ServerUtils server;
    private Task task;
    private Tag tag;
    @FXML
    private Label name;
    @FXML
    private Rectangle colorPreview;
    @FXML
    private JFXButton button;

    /**
     * Creates a new {@link AddTagListingCtrl} onject.
     *
     * @param server is the server.
     * @param mainCtrl is the mainCtrl.
     */
    @Inject
    public AddTagListingCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Gets the task.
     *
     * @return the task.
     */
    public Task getTask() {
        return task;
    }

    /**
     * Sets the task.
     *
     * @param task is the task.
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Gets the tag.
     *
     * @return the tag.
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Sets the tag.
     *
     * @param tag is the tag.
     */
    public void setTag(Tag tag) {
        this.tag = tag;
        refresh();
    }

    /**
     * Adds the tag to the task.
     */
    public void add() {
        task.tags.add(tag);
        tag.tasks.add(task);
        refresh();
        mainCtrl.setTaskWithTags(task);
    }

    /**
     * Removes the tag from the task.
     */
    public void remove() {
        task.tags.remove(tag);
        tag.tasks.remove(task);
        refresh();
        mainCtrl.setTaskWithTags(task);
    }

    /**
     * Refreshes the scene.
     */
    public void refresh() {
        this.name.setText(tag.name);
        Paint paint = Paint.valueOf(tag.color);
        colorPreview.setFill(paint);
        if (task.tags.contains(tag)) {
            button.setText("Remove");
            button.setOnAction(event -> remove());
            button.setStyle("-fx-background-color: #860000;");
        }
        else {
            button.setText("Add");
            button.setOnAction(event -> add());
            button.setStyle("-fx-background-color: #000000;");
        }
    }
}
