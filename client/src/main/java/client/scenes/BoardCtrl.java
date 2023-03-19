package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;

public class BoardCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Board board;
    @FXML
    private TitledPane boardView;
    @FXML
    private HBox listContainer;

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
    }

    /**
     * Initializes this Object.
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardView.setText("No board to be displayed");
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
        boardView.setText(board.name + " (" + board.id + ")");
    }

    /**
     * Getter for the boardView, which is the root element.
     *
     * @return the boardView.
     */
    public TitledPane getBoardView() {
        return boardView;
    }

    /**
     * Setter for boardView.
     *
     * @param boardView is the TitledPane.
     */
    public void setBoardView(TitledPane boardView) {
        this.boardView = boardView;
    }

    /**
     * Refreshes this Object.
     */
    public void refresh() {}

}
