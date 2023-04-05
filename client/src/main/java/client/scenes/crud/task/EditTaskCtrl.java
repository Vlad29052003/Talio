package client.scenes.crud.task;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Task;
import jakarta.ws.rs.WebApplicationException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javax.inject.Inject;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class EditTaskCtrl {
    private MainCtrl mainCtrl;
    private ServerUtils server;
    private Task task;
    @FXML
    TextField name;
    @FXML
    TextArea description;
    @FXML
    ColorPicker colorPicker;

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
    }

    /**
     * Autofocuses the first field.
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
     * Initiates the edit operation.
     */
    public void edit() {
        if (name.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There name cannot be empty!\r");
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
            server.updateTask(task);
            mainCtrl.updateTaskInList(task);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The board was not found on the server!" +
                    "\rIt will be removed from the workspace!");
            alert.showAndWait();
            mainCtrl.cancel();
            return;
        }
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }

    /**
     * Cancels the action.
     */
    public void cancel() {
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }
}
