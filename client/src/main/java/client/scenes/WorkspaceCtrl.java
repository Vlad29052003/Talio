package client.scenes;

import client.datasaving.ClientData;
import client.datasaving.JoinedBoardList;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @FXML
    private TextField serverIP;
    @FXML
    private Label connectionStatus;

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

        try {
            this.file = new File("client/src/main/resources/files/clientData.txt");
            if (file.length() == 0) {
                data = new ClientData();
                data.addJoinedBoardList(new JoinedBoardList("http://localhost:8080/"));
                new Thread(this::writeToFile).start();
            } else new Thread(this::readData).start();;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes this Workspace.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int index = data.getLastActiveOn();
        String ip = data.getServers().get(index).getServer();
        serverIP.setText(ip);
        fetch();
    }

    /**
     * Reads the file that contains information about joined Boards.
     */
    public synchronized void readData() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.file));
            this.data = (ClientData) in.readObject();
            in.close();
        } catch (Exception e) {
            data = new ClientData();
            data.addJoinedBoardList(new JoinedBoardList("http://localhost:8080/"));
            new Thread(this::writeToFile).start();
            displayError("There has been an error reading the file! Its content has been reset.");
        }
    }

    /**
     * Writes the current state of the object to the file.
     */
    public synchronized void writeToFile() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.file));
            out.writeObject(this.data);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a Board to the data object.
     *
     * @param id is the id of the added Board.
     */
    public void addBoardToData(long id) {
        int index = data.getLastActiveOn();
        data.getServers().get(index).addBoard(id);
        new Thread(this::writeToFile).start();
    }

    /**
     * Removes a Board from the data object.
     *
     * @param id is the id of the Board to be removed.
     */
    public void removeBoardFromData(long id) {
        int index = data.getLastActiveOn();
        data.getServers().get(index).removeBoard(id);
        new Thread(this::writeToFile).start();
    }

    /**
     * Populates this Workspace with the previously
     * joined/created Boards.
     */
    public void loadBoardsFromFile() {
        reset();
        ArrayList<Long> toBeRemoved = new ArrayList<>();
        boolean modified = false;
        int index = data.getLastActiveOn();

        if (determineConnectionStatus()) {
            for (long id : data.getServers().get(index).getBoardIDs()) {
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
            if (modified) {
                for (long id : toBeRemoved) {
                    data.getServers().get(index).removeBoard(id);
                }
                new Thread(this::writeToFile).start();
            }
        } else {
            displayError("Previously joined boards cannot be loaded if the server is offline!");
        }
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
     * Gets the data.
     *
     * @return the data.
     */
    public ClientData getData() {
        return data;
    }

    /**
     * Sets the data.
     *
     * @param data the data.
     */
    public void setData(ClientData data) {
        this.data = data;
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
     * Sets the file.
     *
     * @param file is the file.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Switches to the AddBoard Scene
     */
    public void addBoard() {
        mainCtrl.addBoard();
    }

    /**
     * Checks if a connection with the provided server ip can be
     * established. Executed when Fetch button is pressed.
     */
    public void fetch() {
        server.setServer(serverIP.getText());
        Optional<JoinedBoardList> jbl = data.getServers()
                .stream().filter(s -> s.getServer().equals(serverIP.getText())).findFirst();
        if (jbl.isPresent()) {
            int index = data.getJoinedBoardPosition(jbl.get());
            data.setLastActiveOn(index);
            new Thread(this::writeToFile).start();
            loadBoardsFromFile();
        } else {
            data.addJoinedBoardList(new JoinedBoardList(serverIP.getText()));
            new Thread(this::writeToFile).start();
            reset();
        }
    }

    /**
     * Determines if the server can be reached and sets the connectionStatus label
     * to display an informative message.
     *
     * @return true if the server can be reached,
     * false otherwise.
     */
    public boolean determineConnectionStatus() {
        try {
            server.testConnection();
            connectionStatus.setText("The server is up and running!");
            connectionStatus.setStyle("-fx-text-fill: #046600;");
            return true;
        } catch (Exception e) {
            connectionStatus.setStyle("-fx-text-fill: #880606;");
            connectionStatus.setText("The IP may be incorrect or the server may be offline.");
            return false;
        }
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

    /**
     * Displays an error alert.
     *
     * @param message is the message of the error.
     */
    public void displayError(String message) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Resets this WorkspaceCtrl and the Board that is displayed.
     */
    public void reset() {
        boardWorkspace.getChildren().clear();
        boards.clear();
        mainCtrl.switchBoard(null);
    }
}
