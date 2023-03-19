package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WorkspaceCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private List<BoardDisplayWorkspace> boards;
    @FXML
    private AnchorPane boardViewPane;
    @FXML
    private VBox boardWorkspace;

    /**
     * Creates a new {@link WorkspaceCtrl workspace controller}
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
     * Initializes the object.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Gets the server.
     *
     * @return the server.
     */
    public ServerUtils getServer() {
        return server;
    }

    /**
     * Gets the mainCtrl.
     *
     * @return the mainCtrl.
     */
    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    /**
     * Gets the boards.
     *
     * @return the boards.
     */
    public List<BoardDisplayWorkspace> getBoards() {
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
        BoardDisplayWorkspace displayBoard = mainCtrl.loadBoardDisplayWorkspace(newBoard);
        boards.add(displayBoard);
        boardWorkspace.getChildren().add(displayBoard.getRoot());
    }

    /**
     * Removes a BoardDisplayWorkspace from the workspace.
     *
     * @param boardDisplayWorkspace is the BoardDisplayWorkspace to be removed.
     */
    public void removeFromWorkspace(BoardDisplayWorkspace boardDisplayWorkspace) {
        boards.remove(boardDisplayWorkspace);
        boardWorkspace.getChildren().remove(boardDisplayWorkspace.getRoot());
    }

    /**
     * Removed a Board from the workspace.
     *
     * @param removed is the Board to be removed.
     */
    public void removeFromWorkspace(Board removed) {
        BoardDisplayWorkspace boardDisplayWorkspace =
                boards.stream().filter(b -> b.getBoardCtrl()
                        .getBoard().equals(removed)).findFirst().get();
        boards.remove(boardDisplayWorkspace);
        boardWorkspace.getChildren().remove(boardDisplayWorkspace.getRoot());
    }

    /**
     * Updates a Board on the workspace.
     *
     * @param board is the Board to be updated.
     */
    public void updateBoard(Board board) {
        var toBeUpdated =
                boards.stream().filter(b -> b.getBoardCtrl()
                        .getBoard().id == board.id).findFirst();
        if (toBeUpdated.isEmpty()) return;
        var updatedBoardWorkspace = toBeUpdated.get();
        updatedBoardWorkspace.getBoardCtrl().setBoard(board);
        updatedBoardWorkspace.refresh();
    }
}
