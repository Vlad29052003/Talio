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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BoardCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Board board;
    private ArrayList<TaskListCtrl> listControllers = new ArrayList<>();
    @FXML
    private Button boardPassword;
    @FXML
    private Button unlockBoard;
    @FXML
    private Label boardTitle;
    @FXML
    private HBox header;
    @FXML
    private Button addListButton;
    @FXML
    private Button tagsButton;
    @FXML
    private HBox listContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;

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

    public void setBoardWithoutRefresh(Board board) {
        this.board = board;
    }

    /**
     * Refreshes the name of the Board.
     */
    private void refreshBoardHeader() {
        if (board != null) {
            boardTitle.setText(board.name + " (id: " + board.id + ")");
            addListButton.setVisible(true);
            tagsButton.setVisible(true);
            unlockBoard.setVisible(true);
            boardPassword.setVisible(true);
        } else {
            boardTitle.setText("No board to be displayed");
            addListButton.setVisible(false);
            tagsButton.setVisible(false);
            unlockBoard.setVisible(false);
            boardPassword.setVisible(false);
        }
    }

    /**
     * Re-render the board view UI.
     * This will refresh all task lists and tasks currently rendered.
     */
    public void refresh() {
        refreshBoardHeader();

        this.listContainer.getChildren().clear();
        this.listContainer.setStyle("-fx-background-color: #f4f4f4;");
        this.header.setStyle("-fx-background-color: #f4f4f4;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 0 0 1 0;");
        this.boardTitle.setTextFill(Paint.valueOf("#000000"));
        this.listControllers = new ArrayList<>();
        this.scrollPane.setStyle("-fx-border-color: white;");
        this.anchorPane.setStyle("-fx-border-color: white;");
        if (this.board == null) return;

        if (!this.board.backgroundColor.equals("")) {
            this.listContainer.setStyle("-fx-background-color: " + this.board.backgroundColor);
            this.header.setStyle("-fx-background-color: " + this.board.backgroundColor +
                    ";-fx-border-color: " + this.board.fontColor +
                    ";-fx-border-width: 0 0 1 0;");
            this.scrollPane.setStyle("-fx-border-color: " + this.board.backgroundColor);
            this.anchorPane.setStyle("-fx-border-color: " + this.board.backgroundColor);
        }
        if (!this.board.fontColor.equals("")) {
            this.boardTitle.setTextFill(Paint.valueOf(this.board.fontColor));
        }

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
            if(mainCtrl.getAdmin() || board.isEditable()){
                mainCtrl.addTaskList(board);
            }else{
                mainCtrl.unlockBoard(board);
            }
        }
    }

    /**
     * Displays the tag overview.
     */
    public void tagOverview() {
        if(mainCtrl.getAdmin() || board.isEditable()){
            mainCtrl.tagOverview(board);
        }else{
            mainCtrl.unlockBoard(board);
        }
    }

    /**
     * Checks if you have the read / write permission for the board.
     * In case you are missing it you have to input it.
     */
    public void unlockBoard() {
        if(mainCtrl.getAdmin() || board.isEditable()){
            mainCtrl.youHavePermission();
        }else{
            mainCtrl.unlockBoard(board);
        }
    }

    /**
     * Checks if the user has administrator privileges and edits the board password.
     * If not the scene is changed to the password input.
     */
    public void editBoardPassword() {
        if (mainCtrl.getAdmin() || board.isEditable()) {
            mainCtrl.editBoardPassword(board);
        } else {
            mainCtrl.unlockBoard(board);
        }
    }

    /**
     * Resets the background to transparent.
     * (removes the highlight)
     */
    public void resetFocus() {
        listControllers.stream().flatMap(lc -> lc.getTaskControllers()
                        .stream()).filter(tc -> tc.getTask().focused)
                .forEach(TaskCtrl::resetFocus);
    }

    /**
     * Gets the index of the next TaskList
     *
     * @param taskList current TaskList
     * @param index    of the current TaskList
     */
    public void getNextIndex(TaskList taskList, int index) {
        listControllers.stream().
                filter(tlc -> tlc.getTaskList().id == taskList.id)
                .forEach(tlc -> tlc.getNextIndex(index));
    }

    /**
     * Gets the neighbouring index from the adjacent TaskList
     *
     * @param taskList current TaskList
     * @param index    of the current TaskList
     * @param isRight  right/left TaskList
     */
    public void getNeighbourIndex(TaskList taskList, int index, boolean isRight) {
        Comparator<TaskList> idComparator = Comparator.comparingLong(tl -> tl.id);
        List<TaskList> sortedTaskList = board.lists.stream()
                .sorted(idComparator).collect(Collectors.toList());
        int i = sortedTaskList.indexOf(taskList);

        TaskList nextTaskList;

        if (isRight) {
            if (i == board.lists.size() - 1) {
                nextTaskList = sortedTaskList.get(0);
            } else {
                nextTaskList = sortedTaskList.get(i + 1);
            }
        } else {
            if (i == 0) {
                nextTaskList = sortedTaskList.get(board.lists.size() - 1);
            } else {
                nextTaskList = sortedTaskList.get(i - 1);
            }
        }

        int step = 0;
        while (step < sortedTaskList.size() && nextTaskList.tasks.size() == 0) {
            if (isRight) {
                i++;
            } else {
                i--;
            }
            if (i >= sortedTaskList.size()) {
                i = 0;
            }
            if (i < 0) {
                i = sortedTaskList.size() - 1;
            }
            nextTaskList = sortedTaskList.get(i);
            step ++;
        }

        TaskList finalNextTaskList = nextTaskList;
        listControllers.stream()
                .filter(lc -> lc.getTaskList().id == finalNextTaskList.id)
                .forEach(lc -> lc.getNeighbour(index));
    }
}
