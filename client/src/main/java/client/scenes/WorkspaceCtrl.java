package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorkspaceCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private List<BoardListingCtrl> boards;
    @FXML
    private AnchorPane boardViewPane;
    @FXML
    private VBox boardWorkspace;

    /**
     * Creates a new {@link WorkspaceCtrl} instance.
     *
     * @param server   is the ServerUtils
     * @param mainCtrl is the MainCtrl
     */
    @Inject
    public WorkspaceCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        boards = new ArrayList<>();
    }

    /**
     * Gets the boards.
     *
     * @return the boards.
     */
    public List<BoardListingCtrl> getBoards() {
        return this.boards;
    }

    /**
     * Gets the boardViewPane.
     *
     * @return the boardViewPane.
     */
    public AnchorPane getBoardViewPane() {
        return this.boardViewPane;
    }

    /**
     * Sets the boardViewPane.
     *
     * @param pane the boardViewPane.
     */
    public void setBoardViewPane(AnchorPane pane) {
        this.boardViewPane = pane;
    }

    /**
     * Gets the boardWorkspace.
     *
     * @return the boardWorkspace.
     */
    public VBox getBoardWorkspace() {
        return boardWorkspace;
    }

    /**
     * Sets the boardWorkspace.
     *
     * @param boardWorkspace is the boardWorkspace.
     */
    public void setBoardWorkspace(VBox boardWorkspace) {
        this.boardWorkspace = boardWorkspace;
    }

    /**
     * Switches to the AddBoard Scene
     */
    public void addBoard() {
        mainCtrl.addBoard();
    }

    /**
     * Switches to the JoinBoard Scene.
     */
    public void joinBoard() {
        mainCtrl.joinBoard();
    }

    /**
     * TO BE IMPLEMENTED
     */
    public void admin() {
        /* TODO */
    }

    /**
     * Method used to embed the BoardCtrl in the same Scene.
     *
     * @param boardRoot is the root of the BoardCtrl.
     */
    public void setBoardView(Parent boardRoot) {
        this.boardViewPane.getChildren().clear();
        this.boardViewPane.getChildren().add(boardRoot);
        AnchorPane.setTopAnchor(boardRoot, 0.0);
        AnchorPane.setLeftAnchor(boardRoot, 0.0);
        AnchorPane.setRightAnchor(boardRoot, 0.0);
        AnchorPane.setBottomAnchor(boardRoot, 0.0);
    }

    /**
     * Adds a Board to the workspace.
     *
     * @param newBoard is the board to be added.
     */
    public void addBoardToWorkspace(Board newBoard) {
        if(mainCtrl.isPresent(newBoard)) return;
        var pair = mainCtrl.newBoardListingView(newBoard);
        boards.add(pair.getKey());
        boardWorkspace.getChildren().add(pair.getValue());
    }

    /**
     * Removes a BoardListingCtrl from the workspace.
     *
     * @param boardListingCtrl is the BoardListingCtrl to be removed.
     */
    public void removeFromWorkspace(BoardListingCtrl boardListingCtrl) {
        boards.remove(boardListingCtrl);
        boardWorkspace.getChildren().remove(boardListingCtrl.getRoot());
    }

    /**
     * Removed a Board from the workspace.
     *
     * @param removed is the Board to be removed.
     */
    public void removeFromWorkspace(Board removed) {
        List<BoardListingCtrl> toBeRemoved = boards.stream()
            .filter(b -> b.getBoard().equals(removed))
            .collect(Collectors.toList());
        toBeRemoved.forEach(this::removeFromWorkspace);
    }

    /**
     * Removed a Board from the workspace.
     *
     * @param id is the id of the Board to be removed.
     */
    public void removeFromWorkspace(long id) {
        List<BoardListingCtrl> toBeRemoved = boards.stream()
            .filter(b -> b.getBoard().id == id)
            .collect(Collectors.toList());
        toBeRemoved.forEach(this::removeFromWorkspace);
    }

    /**
     * Updates a Board on the workspace.
     *
     * @param board is the Board to be updated.
     */
    public void updateBoard(Board board) {
        var toBeUpdated =
                boards.stream().filter(b -> b.getBoard().id == board.id).findFirst();
        if (toBeUpdated.isEmpty()) return;
        var updatedBoardWorkspace = toBeUpdated.get();
        updatedBoardWorkspace.setBoard(board);
        updatedBoardWorkspace.refresh();
    }
}
