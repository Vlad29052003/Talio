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
     * Constructor to be called by {@link com.google.inject.Injector}.
     * @param server the {@link ServerUtils} instance to use
     * @param mainCtrl reference to the {@link MainCtrl} instance
     */
    @Inject
    public BoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(board != null)
            boardView.setText(board.name + " (" + board.id + ")");
        else boardView.setText("No board to be displayed");
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
     * Re-render the board view UI.
     * This will refresh all task lists and tasks currently rendered.
     */
    public void refresh() {

    }

}
