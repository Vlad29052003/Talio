package client.scenes;

import client.Main;
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
    private int inc; //used temporary to generate names
    @FXML
    private AnchorPane boardViewPane;
    @FXML
    private VBox boardWorkspace;

    /**
     * Constructor to be called by {@link com.google.inject.Injector}.
     * @param server the {@link ServerUtils} instance to use
     * @param mainCtrl reference to the {@link MainCtrl} instance
     */
    @Inject
    public WorkspaceCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        inc = 0;
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
     * Get the {@link ServerUtils} instance.
     * @return the {@link ServerUtils} instance used by this instance
     */
    public ServerUtils getServer() {
        // TODO: Review this function. The ServerUtils instance is only for this instance' internal
        //       usage, thus shouldn't have a getter. The tests complicate this though, so we should
        //       maybe have a look at what our options are.
        return server;
    }

    /**
     * Get the {@link MainCtrl} instance.
     * @return the {@link MainCtrl} instance used by this instance
     */
    public MainCtrl getMainCtrl() {
        // TODO: Same as for getServer()
        return mainCtrl;
    }

    /**
     * Get the list of known boards.
     * @return a {@link List<BoardDisplayWorkspace>} of all boards known by the workspace
     */
    public List<BoardDisplayWorkspace> getBoards() {
        return boards;
    }

    /**
     * Get the {@link javafx.scene.layout.Pane} container in which boards are to be rendered.
     * @return the {@link VBox} for rendering boards in
     */
    public VBox getBoardWorkspace() {
        return boardWorkspace;
    }

    /**
     * Sends a request to the server to create a new Board,
     * creates a button that switches the embedded boardView
     * to the newly created one, adds the button in the Workspace.
     * Is called when "Create Board" button is pressed.
     */
    public void addBoard() {
        mainCtrl.createBoard();
    }

    /**
     * Add a {@link Board} to the board listing in the workspace.
     * @param newBoard the {@link Board} to be added.
     */
    public void addBoardToWorkspace(Board newBoard) {
        BoardDisplayWorkspace displayBoard = createDisplay(newBoard);
        boards.add(displayBoard);
        boardWorkspace.getChildren().add(displayBoard.getRoot());
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
     * Creates a new {@link BoardDisplayWorkspace} object.
     *
     * @param newBoard is the Board it is associated with.
     * @return the new object.
     */
    private BoardDisplayWorkspace createDisplay(Board newBoard) {
        BoardCtrl newBoardCtrl = createInstance(newBoard);
        BoardDisplayWorkspace boardDisplay = Main.getMyFXML().
                load(BoardDisplayWorkspace.class,
                        "client", "scenes", "BoardDisplayWorkspace.fxml").getKey();
        boardDisplay.setBoardCtrl(newBoardCtrl);
        return boardDisplay;
    }

    /**
     * Creates a new instance of {@link BoardCtrl} in order to allow
     * for efficient switching to another Board.
     *
     * @param newBoard is the Board object associated with this controller.
     * @return the BoardCtrl.
     */
    public BoardCtrl createInstance(Board newBoard) {
        BoardCtrl boardCtrl = Main.getMyFXML()
                .load(BoardCtrl.class, "client", "scenes", "BoardView.fxml").getKey();
        boardCtrl.setBoard(newBoard);
        return boardCtrl;
    }

    /**
     * TO BE IMPLEMENTED
     */
    public void admin() {
        /* TODO */
    }

    /**
     * Removes a {@link BoardDisplayWorkspace} from the workspace
     *
     * @param boardDisplayWorkspace is the BoardDisplayWorkspace to be removed
     */
    public void removeFromWorkspace(BoardDisplayWorkspace boardDisplayWorkspace) {
        boards.remove(boardDisplayWorkspace);
        boardWorkspace.getChildren().remove(boardDisplayWorkspace.getRoot());
    }
}
