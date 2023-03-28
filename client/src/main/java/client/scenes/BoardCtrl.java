package client.scenes;

import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.Board;
import commons.TaskList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class BoardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Board board;
    @FXML
    private Label boardTitle;
    @FXML
    private HBox listContainer;
    private List<TaskListController> tasklists;

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
        tasklists = new ArrayList<>();
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
        this.listContainer.getChildren().clear();
        resetBoardName();
    }

    /**
     * Resets the name of the Board.
     */
    private void resetBoardName() {
        if (board != null) {
            boardTitle.setText(board.name + " (" + board.id + ")");
        } else boardTitle.setText("No board to be displayed");
    }

    /**
     * Resets the contents of the tasklists and listContainer HBox.
     */
    public void resetLists() {
        listContainer.getChildren().clear();
        tasklists.clear();
        if (board != null) {
            for (TaskList taskList : board.getTaskLists()) {
                addTaskListToBoard(taskList);
            }
        }
    }

    /**
     * Refreshes this Object.
     */
    public void refresh() {
        resetBoardName();
        resetLists();
    }

    /**
     * Switches to the AddTaskList Scene
     */
    public void addTaskList() {
        if (board != null) {
            mainCtrl.addTaskList(board);
        }
    }

    public void createTaskList() {
        if (board != null) {
            TaskList tlist = new TaskList("tasklist", 1);
            TaskListController tlc = mainCtrl.loadTaskListController(tlist);
            listContainer.getChildren().add(tlc.getRoot());
            tasklists.add(tlc);
        }
    }

    /**
     * Adds a TaskList to the workspace.
     *
     * @param newTaskList is the TaskList to be added.
     */
    public void addTaskListToBoard(TaskList newTaskList) {
        TaskListController taskList = mainCtrl.loadTaskListController(newTaskList);
        listContainer.getChildren().add(taskList.getRoot());
        tasklists.add(taskList);

    }

    /**
     * Removed a TaskList from the board.
     *
     * @param removed is the TaskList to be removed.
     */
    public void removeTaskListFromBoard(TaskList removed) {
        TaskListController taskList =
                tasklists.stream().filter(b -> b.getTaskList().equals(removed)).findFirst().get();
        tasklists.remove(taskList);
        listContainer.getChildren().remove(taskList.getRoot());
    }


}
