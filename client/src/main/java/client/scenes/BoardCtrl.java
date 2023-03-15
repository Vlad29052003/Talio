package client.scenes;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.nio.Buffer;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class BoardCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Board board;
    @FXML
    private TitledPane boardView;
    @FXML
    private HBox listContainer;


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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void refresh() {
        boardView.setText(board.name + " (" + board.id + ")");
    }

}
