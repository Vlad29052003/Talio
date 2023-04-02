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

    @Inject
    public AddTagListingCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        refresh();
    }

    public void add() {
        task.tags.add(tag);
        tag.tasks.add(task);
        refresh();
        mainCtrl.refreshEditTask(task);
    }

    public void remove() {
        task.tags.remove(tag);
        tag.tasks.remove(task);
        refresh();
        mainCtrl.refreshEditTask(task);
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
