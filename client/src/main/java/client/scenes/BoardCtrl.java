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
     * @param server is the ServerUtils.
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
        if(board != null) {
            boardTitle.setText(board.name + " (" + board.id + ")");
        }
        else boardTitle.setText("No board to be displayed");
    }

    /**
     * Refreshes this Object.
     */
    public void refresh() {
        resetBoardName();
    }

    public void createTaskList(){
        if (board != null) {
            TaskList tlist = new TaskList("tasklist", 1);
            TaskListController tlc = mainCtrl.loadTaskListController(tlist);
            listContainer.getChildren().add(tlc.getRoot());
            tasklists.add(tlc);
        }
    }


}
