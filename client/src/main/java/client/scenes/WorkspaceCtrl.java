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

        boolean flag = false;

        try {
            this.file = new File("client/src/main/resources/files/clientData.txt");
            if (file.length() == 0) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!flag) {
            readData();
        } else {
            data = new ClientData();
            JoinedBoardList jbl = new JoinedBoardList("http://localhost:8080/");
            data.addJoinedBoardList(jbl);
            Thread writeData = new Thread(this::writeToFile);
            writeData.start();
        }
    }

    /**
     * Reads the file that contains information about joined Boards.
     * Populates the workspace with the previously joined/created Boards.
     */
    private void readData() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
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
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(data);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JoinedBoardList jbl = data.getServers().get(data.getLastActiveOn());
        boolean flag = false;
        for(long id : jbl.getBoardIDs()) {
            try {
                Board board = server.joinBoard(id);
                //var pair = mainCtrl.loadBoardDisplayWorkspace(board);
            }
            catch (Exception e) {
                jbl.removeBoard(id);
                flag = true;
            }
        }
        if(flag)writeToFile();
    }

    public void addBoardToData(long id) {
        System.out.println(data);
        data.getServers().get(data.getLastActiveOn()).addBoard(id);
        System.out.println(data);
        Thread writeData = new Thread(this::writeToFile);
        writeData.start();
    }

    public void removeBoardFromData(long id) {
        data.getServers().get(data.getLastActiveOn()).removeBoard(id);
        Thread writeData = new Thread(this::writeToFile);
        writeData.start();
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
        BoardListingCtrl boardListingCtrl =
                boards.stream().filter(b -> b.getBoard().equals(removed)).findFirst().get();
        boards.remove(boardListingCtrl);
        boardWorkspace.getChildren().remove(boardListingCtrl.getRoot());
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
