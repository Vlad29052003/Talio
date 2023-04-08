package client.scenes.crud.task;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Tag;
import commons.Task;
import jakarta.ws.rs.WebApplicationException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javax.inject.Inject;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class EditTaskCtrl {
    private MainCtrl mainCtrl;
    private ServerUtils server;
    private Task task;
    private Task edited;
    private Text noTagStatus;
    @FXML
    private TextField name;
    @FXML
    private TextArea description;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private VBox tagContainer;

    /**
     * Creates a new {@link EditTaskCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public EditTaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.edited = new Task();
    }

    /**
     * Autofocuses the first field.
     * Sets the keyboard shortcuts for ENTER and ESC.
     * @param task The {@link Task} we're editing
     */
    public void initialize(Task task) {
        this.setTask(task);
        this.name.setText(task.name);
        this.description.setText(task.description);
        if(this.task.color.equals(""))
            this.colorPicker.setValue(Color.valueOf("#f4f4f4"));
        else
            this.colorPicker.setValue(Color.valueOf(this.task.color));
        Platform.runLater(() -> name.requestFocus());

        this.edited = new Task();
        edited.tags.addAll(task.tags);
        name.setText(task.name);
        description.setText(task.description);
        noTagStatus = new Text("   There are no tags associated with this board.");
        tagContainer.getChildren().clear();
        if (task.getTaskList().board.tags.size() == 0) {
            tagContainer.getChildren().add(noTagStatus);
        }
        for (Tag tag : task.getTaskList().board.tags) {
            var pair = mainCtrl.newAddTagListingView(tag, edited);
            tagContainer.getChildren().add(pair.getValue());
        }

        this.name.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.ENTER) {
                edit();
                event.consume();
            }
            else if (keyCode == KeyCode.ESCAPE) {
                cancel();
                event.consume();
            }
        });
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
     * Gets the edited.
     *
     * @return the edited task.
     */
    public Task getEdited() {
        return edited;
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
     * Sets the task without refreshing the text fields.
     * Used to add the tags.
     *
     * @param task is the task with tags.
     */
    public void getTagUpdates(Task task) {
        this.edited.tags = task.tags;
    }

    /**
     * Initiates the edit operation.
     */
    public void edit() {
        if (name.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The name cannot be empty!\r");
            alert.showAndWait();
            return;
        }

        DoubleFunction<String> fmt = v -> {
            String in = Integer.toHexString((int) Math.round(v * 255));
            return in.length() == 1 ? "0" + in : in;
        };
        Function<Color, String> toHex = v -> "#" + (
                fmt.apply(v.getRed()) + fmt.apply(v.getGreen())
                        + fmt.apply(v.getBlue()) + fmt.apply(v.getOpacity())
        ).toUpperCase();
        String taskColor = toHex.apply(this.colorPicker.getValue());

        try {
            task.name = name.getText();
            task.description = description.getText();
            task.color = taskColor;
            task.tags = edited.tags;
            server.updateTask(task);
        } catch (WebApplicationException e) {
            e.printStackTrace();
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The task was not found on the server!");
            alert.showAndWait();
            mainCtrl.cancel();
            return;
        }
        mainCtrl.hidePopup();
    }

    /**
     * Cancels the action.
     */
    public void cancel() {
        this.edited = new Task();
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }
}
