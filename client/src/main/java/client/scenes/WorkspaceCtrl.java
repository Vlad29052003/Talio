package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
    private VBox boardButtons;

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
     * Method used to embed the BoardCtrl in the same Scene.
     *
     * @param boardRoot is the root of the BoardCtrl.
     */
    void setBoardView(Parent boardRoot) {
        this.boardViewPane.getChildren().clear();
        this.boardViewPane.getChildren().add(boardRoot);
        AnchorPane.setTopAnchor(boardRoot, 0.0);
        AnchorPane.setLeftAnchor(boardRoot, 0.0);
        AnchorPane.setRightAnchor(boardRoot, 0.0);
        AnchorPane.setBottomAnchor(boardRoot, 0.0);
    }

    /**
     * Sends a request to the server to create a new Board,
     * creates a button that switches the embedded boardView
     * to the newly created one, adds the button in the Workspace.
     * Is called when "Create Board" button is pressed.
     */
    public void addBoard() {
        Board newBoard = new Board("name" + inc, "");
        inc++;

        try {
            newBoard = server.addBoard(newBoard);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        BoardDisplayWorkspace displayBoard = createDisplay(newBoard);
        boards.add(displayBoard);
        boardButtons.getChildren().add(displayBoard.getRoot());
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
        boardButtons.getChildren().remove(boardDisplayWorkspace.getRoot());
    }
}
