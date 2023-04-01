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

    /**
     * Creates a new {@link TagOverviewCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
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

    /**
     * Populates the container with the tags.
     */
    public void populate() {
        tagContainer.getChildren().clear();
        board.sortTags();
        if (board != null) {
            for (Tag tag : board.tags) {
                var pair = mainCtrl.newTagListingView(tag);
                tags.add(pair.getKey());
                tagContainer.getChildren().add(pair.getValue());
            }
        }
    }

    /**
     * Gets the board.
     *
     * @return the board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the board.
     *
     * @param board is the Board.
     */
    public void setBoard(Board board) {
        this.board = board;
        refresh();
    }

    /**
     * Switches to create tag scene.
     */
    public void createTag() {
        mainCtrl.addTag(board);
    }

    /**
     * Closes the scene.
     */
    public void close() {
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }

    /**
     * Refreshes the scene.
     */
    public void refresh() {
        populate();
    }
}
