package client.scenes.crud.task;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Task;
import jakarta.ws.rs.WebApplicationException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.util.Callback;
import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OpenTaskCtrl implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.name.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.ESCAPE) {
                cancel();
                event.consume();
            }
        });
    }

    /**
     * Cancels the action.
     */
    public void cancel() {
        mainCtrl.hidePopup();
    }

    public static class SubTask {
        private final StringProperty name = new SimpleStringProperty();
        private final BooleanProperty completed = new SimpleBooleanProperty();

        /**
         * Creates a new subtask from a name and a boolean.
         *
         * @param name       is the name of the subtask.
         * @param completed  is the boolean representing whether the subtask is completed.
         * @param task       is the task that contains the subtask.
         * @param controller the controller for the UI.
         */
        public SubTask(String name, boolean completed, Task task, OpenTaskCtrl controller) {
            setName(name);
            setCompleted(completed);

            ChangeListener<Boolean> listener = (observable, oldValue, newValue) -> {
                task.setSubTask(getName(), newValue);
                controller.refresh();
            };

            this.completed.addListener(listener);
        }

        /**
         * create a subtask from how it is represented in a task (a "0" or a "1" is appended
         * representing whether the subtask is completed.)
         *
         * @param abstractName the representation of a subtask in a task.
         * @param task         the task that contains the subtask.
         * @param controller   the UI controller.
         */
        public SubTask(String abstractName, Task task, OpenTaskCtrl controller) {
            this(
                    abstractName.substring(0, abstractName.length() - 1),
                    abstractName.endsWith("1"),
                    task,
                    controller
            );
        }

        /**
         * get the property of the name.
         *
         * @return the string property.
         */
        public final StringProperty nameProperty() {
            return this.name;
        }

        /**
         * get the name of the subtask.
         *
         * @return the name.
         */
        public final String getName() {
            return this.nameProperty().get();
        }

        /**
         * set the name of the subtask.
         *
         * @param name is the name.
         */
        public final void setName(final String name) {
            this.nameProperty().set(name);
        }

        /**
         * get the boolean property that represents whether the subtask is completed.
         *
         * @return the property.
         */
        public final BooleanProperty completedProperty() {
            return this.completed;
        }

        /**
         * sets the completed property.
         *
         * @param completed is whether the subtask is completed.
         */
        public final void setCompleted(final boolean completed) {
            this.completedProperty().set(completed);
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    private final MainCtrl mainCtrl;
    private final ServerUtils server;
    private Task task;
    @FXML
    private Label name;
    @FXML
    private TextArea description;
    @FXML
    private ProgressBar progress;
    @FXML
    private ListView<SubTask> subTasks;

    /**
     * Creates as new {@link OpenTaskCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public OpenTaskCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
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
        progress.setProgress(task.calculateProgress());

        setSubTaskList();
        subTasks.refresh();

        updateTask();
    }

    private void updateTask() {
        try {
            server.updateTask(task);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The board was not found on the server!" +
                    "\rIt will be removed from the workspace!");
            alert.showAndWait();
            cancel();
        }
    }

    private void setSubTaskList() {
        ArrayList<SubTask> subtasks = task.subtasks.stream().map(x -> new SubTask(x, task, this))
                .collect(Collectors.toCollection(ArrayList::new));
        subTasks.setItems(FXCollections.observableArrayList(subtasks));

        var callback = new Callback<SubTask, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(SubTask param) {
                return param.completedProperty();
            }
        };
        subTasks.setCellFactory(CheckBoxListCell.forListView(callback));
    }

    /**
     * Adds a SubTask when the button is clicked.
     */
    public void onAddSubTask() {
        if(mainCtrl.getAdmin() || task.getTaskList().board.isEditable()) {
            TextInputDialog dialog = new TextInputDialog("sub task name");
            dialog.setTitle("");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.setContentText("please enter the sub task text: ");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(this::tryAddSubTask);
        }else{
            mainCtrl.unlockBoard(task.getTaskList().board);
        }
        refresh();
    }

    private void tryAddSubTask(String name) {
        if (!task.addSubTask(name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("A sub task with this name already exists.");

            alert.showAndWait();
        }
    }
}
