package client.scenes;

import client.datasaving.ClientData;
import client.datasaving.JoinedBoardList;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WorkspaceCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private List<BoardListingCtrl> boards;
    private ClientData data;
    private File file;
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

        try {
            this.file = new File("client/src/main/resources/files/clientData.txt");
            if (file.length() == 0) {
                data = new ClientData();
                data.addJoinedBoardList(new JoinedBoardList("http://localhost:8080/"));
                writeToFile();
            }
            else readData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(data);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Long> toBeRemoved = new ArrayList<>();
        boolean modified = false;
        int index = data.getLastActiveOn();
        for(long id : data.getServers().get(index).getBoardIDs()) {
            try {
                Board b = server.joinBoard(id);
                var pair = mainCtrl.newBoardListingView(b);
                boards.add(pair.getKey());
                boardWorkspace.getChildren().add(pair.getValue());
            } catch (Exception e) {
                toBeRemoved.add(id);
                modified = true;
            }
        }
        if(modified) {
            for (long id : toBeRemoved) {
                data.getServers().get(index).removeBoard(id);
            }
            writeToFile();
        }
    }

    /**
     * Reads the file that contains information about joined Boards.
     * Populates the workspace with the previously joined/created Boards.
     */
    private void readData() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.file));
            this.data = (ClientData) in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the current state of the object to the file.
     */
    public void writeToFile() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.file));
            out.writeObject(this.data);
            out.close();

            System.out.println("write: " + data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBoardToData(long id) {
        int index = data.getLastActiveOn();
        data.getServers().get(index).addBoard(id);
        writeToFile();
    }

    public void removeBoardFromData(long id) {
        int index = data.getLastActiveOn();
        data.getServers().get(index).removeBoard(id);
        writeToFile();
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
        var pair = mainCtrl.newBoardListingView(newBoard);
        boards.add(pair.getKey());
        boardWorkspace.getChildren().add(pair.getValue());
        addBoardToData(newBoard.id);
    }

    /**
     * Removes a BoardListingCtrl from the workspace.
     *
     * @param boardListingCtrl is the BoardListingCtrl to be removed.
     */
    public void removeFromWorkspace(BoardListingCtrl boardListingCtrl) {
        boards.remove(boardListingCtrl);
        boardWorkspace.getChildren().remove(boardListingCtrl.getRoot());
        removeBoardFromData(boardListingCtrl.getBoard().id);
    }

    /**
     * Removed a Board from the workspace.
     *
     * @param removed is the Board to be removed.
     */
    public void removeFromWorkspace(Board removed) {
        BoardListingCtrl boardListingCtrl =
                boards.stream().filter(b -> b.getBoard().equals(removed)).findFirst().get();
        boards.remove(boardListingCtrl);
        boardWorkspace.getChildren().remove(boardListingCtrl.getRoot());
        removeBoardFromData(removed.id);
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
