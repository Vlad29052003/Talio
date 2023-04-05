package client.scenes.crud.task;

import commons.Task;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class OpenTaskCtrl {
    private Task task;
    @FXML
    private Label name;
    @FXML
    private TextArea description;
    @FXML
    private ProgressBar progress;
    @FXML
    private ListView<String> subTasks;

    /**
     *
     * Sets the task.
     *
     * @param task the task to be set.
     */
    public void setTask(Task task) {
        this.task = task;
        refresh();
    }

    /**
     * Refreshes the scene.
     */
    public void refresh() {
        name.setText(task.name);
        description.setText(task.description);
        progress.setProgress(0);
        subTasks.setItems(FXCollections.observableArrayList(task.subtasks));
        subTasks.refresh();
    }

    /**
     *
     * Adds a SubTask when the button is clicked.
     *
     */
    public void onAddSubTask() {
        TextInputDialog dialog = new TextInputDialog("sub task name");
        dialog.setTitle("Sub task name");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("please enter the sub task text: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> task.addSubTask(name));
        refresh();
    }
}
