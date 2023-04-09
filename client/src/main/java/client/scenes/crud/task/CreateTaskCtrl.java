package client.scenes.crud.task;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Task;
import commons.TaskList;
import jakarta.ws.rs.WebApplicationException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javax.inject.Inject;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class CreateTaskCtrl {
    private MainCtrl mainCtrl;
    private ServerUtils server;
    private TaskList taskList;
    @FXML
    TextField name;
    @FXML
    TextArea description;
    @FXML
    ColorPicker colorPicker;

    /**
     * Creates a new {@link CreateTaskCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public CreateTaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Autofocuses the first field.
     * Sets the keyboard shortcuts for ENTER and ESC.
     * @param taskListParent The {@link TaskList} this task will be created on
     */
    public void initialize(TaskList taskListParent) {
        this.setTaskList(taskListParent);
        this.name.setText("");
        this.description.setText("");
        this.colorPicker.setValue(Color.valueOf("#f4f4f4"));
        Platform.runLater(() -> name.requestFocus());

        this.name.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.ENTER) {
                add();
                event.consume();
            }
            else if (keyCode == KeyCode.ESCAPE) {
                cancel();
                event.consume();
            }
        });
    }


    /**
     * Gets the TaskList.
     *
     * @return the TaskList.
     */
    public TaskList getTaskList() {
        return taskList;
    }

    /**
     * Sets the taskList.
     *
     * @param taskList is the TaskList.
     */
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Saves the Task on the server.
     */
    public void add() {
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
            Task created = new Task(
                    name.getText(),
                    taskList.tasks.size(),
                    description.getText(),
                    taskColor
            );
            server.addTask(created, taskList.id);
            cancel();
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There has been an error!\r" + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Cancels the action.
     */
    public void cancel() {
        mainCtrl.hidePopup();
    }
}
