package client.scenes;

import client.utils.ServerUtils;
import commons.Board;
import commons.Tag;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TagOverviewCtrl implements Initializable {
    private MainCtrl mainCtrl;
    private ServerUtils server;
    private Board board;
    private List<TagListingCtrl> tags;
    @FXML
    private VBox tagContainer;

    @Inject
    public TagOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        tags = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (board != null)
            populate();
    }

    public void populate() {
        tagContainer.getChildren().clear();
        if (board != null) {
            for (Tag tag : board.tags) {
                var pair = mainCtrl.newTagListingView(tag);
                tags.add(pair.getKey());
                tagContainer.getChildren().add(pair.getValue());
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void createTag() {
        Tag newT = new Tag("name", "");
        board.tags.add(newT);
        refresh();
    }

    public void close() {
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }

    public void refresh() {
        populate();
    }
}
