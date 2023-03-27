package client.scenes;

import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.Board;
import commons.TaskList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
    private void refreshBoardName() {
        if (board != null) {
            boardTitle.setText(board.name + " (id: " + board.id + ")");
        } else boardTitle.setText("No board to be displayed");
    }

    /**
     * Re-render the board view UI.
     * This will refresh all task lists and tasks currently rendered.
     */
    public void refresh() {
        refreshBoardName();

        this.listContainer.getChildren().clear();
        this.listControllers = new ArrayList<>();

        if (board != null) {
            Set<TaskList> taskLists = this.board.lists;
            Iterator<TaskList> it = taskLists.stream()
                    .sorted(Comparator.comparingInt(e -> e.index))
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
    }

}
