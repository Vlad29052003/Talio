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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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
    private List<BoardCtrl> boards;
    private int inc;
    @FXML
    private AnchorPane boardViewPane;
    @FXML
    private VBox boardButtons;



    @Inject
    public WorkspaceCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        inc = 0;
        boards = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Method used to embed the BoardCtrl in the same Scene.
     *
     * @param boardRoot is the root of the BoardCtrl.
     */
    void setBoardView(Parent boardRoot) {
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
        {
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

            Button viewBoard = new Button(newBoard.name + " (" + newBoard.id + ")");
            BoardCtrl newBoardCtrl = createInstance(newBoard);

            viewBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                removeAllChildren();
                mainCtrl.switchBoard(newBoardCtrl);
            });

            boardButtons.getChildren().add(viewBoard);
        }
    }

    /**
     * Removes all children of the boardViewPane AnchorPane
     * in order to allow switching back to a previously visited
     * BoardCtrl.
     */
    public void removeAllChildren() {
        this.boardViewPane.getChildren().clear();
    }

    /**
     * Creates a new instance of BoardCtrl in order to allow
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
}
