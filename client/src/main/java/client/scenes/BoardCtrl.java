package client.scenes;

import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.Board;
import commons.Task;
import commons.TaskList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

public class BoardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Board board;
    private ArrayList<TaskListCtrl> listControllers = new ArrayList<>();
    @FXML
    private Label boardTitle;
    @FXML
    private Button addListButton;
    @FXML
    private HBox listContainer;

    /**
     * Creates a new {@link BoardCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public BoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Getter for the Board.
     *
     * @return the board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Setter for the Board.
     *
     * @param board the new Board.
     */
    public void setBoard(Board board) {
        this.board = board;
        refresh();
    }

    /**
     * Refreshes the name of the Board.
     */
    private void refreshBoardHeader() {
        if (board != null) {
            boardTitle.setText(board.name + " (id: " + board.id + ")");
            addListButton.setVisible(true);
        } else {
            boardTitle.setText("No board to be displayed");
            addListButton.setVisible(false);
        }
    }

    /**
     * Resets the contents of the taskLists and listContainer HBox.
     */
    public void resetLists() {
        listContainer.getChildren().clear();
        listControllers.clear();
        if (board != null) {
            for (TaskList taskList : board.lists) {
                addTaskListToBoard(taskList);
            }
        }
    }

    /**
     * Re-render the board view UI.
     * This will refresh all task lists and tasks currently rendered.
     */
    public void refresh() {
        refreshBoardHeader();

        this.listContainer.getChildren().clear();
        this.listControllers = new ArrayList<>();
        if (this.board == null) return;

        Set<TaskList> taskLists = this.board.lists;
        Iterator<TaskList> it = taskLists.stream()
                .sorted(Comparator.comparingLong(e -> e.id))
                .iterator();
        while (it.hasNext()) {
            TaskList list = it.next();

            Pair<TaskListCtrl, Parent> p = this.mainCtrl.newTaskListView(list);

            TaskListCtrl controller = p.getKey();
            controller.refresh();

            this.listContainer.getChildren().add(p.getValue());
            this.listControllers.add(controller);
        }
    }

    /**
     * Switches to the AddTaskList Scene
     */
    public void addTaskList() {
        if (board != null) {
            mainCtrl.addTaskList(board);
        }
    }

    /**
     * Adds a new TaskList to the Board
     * - method no longer used
     */
    public void createTaskList() {
        if (board != null) {
            TaskList tlist = new TaskList("tasklist");
            TaskListCtrl tlc = mainCtrl.newTaskListView(tlist).getKey();
            listContainer.getChildren().add(tlc.getRoot());
            listControllers.add(tlc);
        }
    }

    /**
     * Adds a TaskList to the workspace.
     *
     * @param newTaskList is the TaskList to be added.
     */
    public void addTaskListToBoard(TaskList newTaskList) {
        TaskListCtrl taskList = mainCtrl.newTaskListView(newTaskList).getKey();
        listContainer.getChildren().add(taskList.getRoot());
        listControllers.add(taskList);

    }

    /**
     * Removes a TaskList from the board.
     *
     * @param removed is the TaskList to be removed.
     */
    public void removeTaskListFromBoard(TaskList removed) {
        TaskListCtrl taskList =
                listControllers.stream().filter(b -> b.getTaskList().equals(removed)).
                        findFirst().get();
        listControllers.remove(taskList);
        listContainer.getChildren().remove(taskList.getRoot());
        board.removeTaskList(removed);
    }

    /**
     * Removes a Task from the board.
     *
     * @param removed is the removed Task.
     */
    public void removeTask(Task removed) {
        TaskListCtrl taskListCtrl =
                listControllers.stream().filter(b -> b.getTaskList().tasks.contains(removed)).
                        findFirst().get();
        taskListCtrl.getTaskList().removeTask(removed);
        taskListCtrl.refresh();
    }

    /**
     * Updates a TaskList on the board.
     *
     * @param updated is the TaskList to be updated.
     */
    public void updateTaskList(TaskList updated) {
        var toBeUpdated =
                listControllers.stream().filter(b -> b.getTaskList().id == updated.id).findFirst();
        if (toBeUpdated.isEmpty()) return;
        var updatedTaskList = toBeUpdated.get();
        updatedTaskList.setTaskList(updated);
        updatedTaskList.refresh();
    }

    /**
     * Updates a Task in the Board.
     *
     * @param updated is the updated Task.
     */
    public void updateTask(Task updated) {
        var toBeUpdated =
                listControllers.stream().filter(b -> b.getTaskList()
                        .tasks.get(updated.index).id == updated.id).findFirst();

        if (toBeUpdated.isEmpty()) return;
        TaskListCtrl tlCtrl = toBeUpdated.get();
        Task found = tlCtrl.getTaskList().tasks.get(updated.index);
        found.name = updated.name;
        found.description = updated.description;
        tlCtrl.refresh();
    }
}
