package client.scenes.crud.board;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import jakarta.ws.rs.WebApplicationException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

import java.util.function.DoubleFunction;

public class EditBoardCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    private TextField text;
    @FXML
    private ColorPicker colorPicker;

    /**
     * Creates a new {@link EditBoardCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public EditBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Autofocuses the first field.
     */
    public void initialize() {
        Platform.runLater(() -> text.requestFocus());
    }

    /**
     * Sets the board.
     *
     * @param board is the Board.
     */
    public void setBoard(Board board) {
        this.board = board;
        this.text.setText(board.name);
        if(this.board.backgroundColor.equals("")) this.board.backgroundColor = "#f4f4f4";
        this.colorPicker.setValue(Color.valueOf(this.board.backgroundColor));
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
     * Bond to the Cancel button.
     * Switches back to the workspace Scene.
     */
    public void cancel() {
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }

    /**
     * Bound to the Confirm button.
     * Sends a request to the server
     * to update this board.
     */
    public void confirm() {
        if (text.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There name cannot be empty!\r");
            alert.showAndWait();
            return;
        }
        this.board.name = text.getText();

        DoubleFunction<String> fmt = (double v) -> {
            String in = Integer.toHexString((int) Math.round(v * 255));
            return in.length() == 1 ? "0" + in : in;
        };
        Color colorValue = this.colorPicker.getValue();
        this.board.backgroundColor = "#" + (
                fmt.apply(colorValue.getRed()) + fmt.apply(colorValue.getGreen())
                        + fmt.apply(colorValue.getBlue()) + fmt.apply(colorValue.getOpacity())
        ).toUpperCase();

        try {
            this.board = server.updateBoard(board);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The board was not found on the server!" +
                    "\rIt will be removed from the workspace!");
            mainCtrl.removeFromWorkspace(this.board);
            alert.showAndWait();
            mainCtrl.cancel();
            this.reset();
            return;
        }

        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }

    /**
     * Resets the fields in this object.
     */
    public void reset() {
        this.board = null;
        text.setText("");
        colorPicker.setValue(new Color(1.0, 1.0, 1.0, 1.0));
    }
}
