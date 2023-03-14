package client.scenes;

import client.utils.ServerUtils;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardTemplateCtrl implements Initializable {
    private Board board;
    private Button displayBoard;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TitledPane boardView;
    @FXML
    private HBox listContainer;

    public BoardTemplateCtrl(ServerUtils server, MainCtrl mainCtrl, Board board, Button displayBoard) {
        this.board = board;
        this.displayBoard = displayBoard;
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardView.setText(this.board.name + " (" + this.board.id + ")");
    }
}
