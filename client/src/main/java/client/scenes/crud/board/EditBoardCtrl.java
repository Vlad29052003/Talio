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
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;

import java.util.function.DoubleFunction;
import java.util.function.Function;

public class EditBoardCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Board board;
    @FXML
    private TextField text;
    @FXML
    private ColorPicker boardBgColorPicker;
    @FXML
    private ColorPicker boardFontColorPicker;
    @FXML
    private ColorPicker listBgColorPicker;
    @FXML
    private ColorPicker listFontColorPicker;

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
     * Sets the keyboard shortcuts for ENTER and ESC.
     */
    public void initialize() {
        Platform.runLater(() -> text.requestFocus());

        this.text.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.ENTER) {
                confirm();
                event.consume();
            }
            else if (keyCode == KeyCode.ESCAPE) {
                cancel();
                event.consume();
            }
        });
    }

    /**
     * Sets the board.
     *
     * @param board is the Board.
     */
    public void setBoard(Board board) {
        this.board = board;
        this.text.setText(board.name);
        if(this.board.backgroundColor.equals(""))
            this.boardBgColorPicker.setValue(Color.WHITE);
        else
            this.boardBgColorPicker.setValue(Color.valueOf(this.board.backgroundColor));
        if(this.board.fontColor.equals(""))
            this.boardFontColorPicker.setValue(Color.valueOf("#000000"));
        else
            this.boardFontColorPicker.setValue(Color.valueOf(this.board.fontColor));

        if(this.board.listBackgroundColor.equals(""))
            this.listBgColorPicker.setValue(Color.WHITE);
        else
            this.listBgColorPicker.setValue(Color.valueOf(this.board.listBackgroundColor));
        if(this.board.listFontColor.equals(""))
            this.listFontColorPicker.setValue(Color.valueOf("#000000"));
        else
            this.listFontColorPicker.setValue(Color.valueOf(this.board.listFontColor));
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
        Platform.runLater(() -> text.requestFocus());
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }

    /**
     * Bound to the Confirm button.
     * Sends a request to the server
     * to update this board.
     */
    public void confirm() {
        Platform.runLater(() -> text.requestFocus());
        if (text.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The name cannot be empty!\r");
            alert.showAndWait();
            return;
        }
        this.board.name = text.getText();

        DoubleFunction<String> fmt = v -> {
            String in = Integer.toHexString((int) Math.round(v * 255));
            return in.length() == 1 ? "0" + in : in;
        };
        Function<Color, String> toHex = v -> "#" + (
                fmt.apply(v.getRed()) + fmt.apply(v.getGreen())
                        + fmt.apply(v.getBlue()) + fmt.apply(v.getOpacity())
        ).toUpperCase();
        this.board.backgroundColor = toHex.apply(this.boardBgColorPicker.getValue());
        this.board.fontColor = toHex.apply(this.boardFontColorPicker.getValue());
        this.board.listBackgroundColor = toHex.apply(this.listBgColorPicker.getValue());
        this.board.listFontColor = toHex.apply(this.listFontColorPicker.getValue());

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
     * Resets the board colors to the default ones.
     */
    public void resetBoardStyle() {
        boardBgColorPicker.setValue(Color.WHITE);
        boardFontColorPicker.setValue(Color.BLACK);
    }

    /**
     * Resets the list colors to the default ones.
     */
    public void resetListStyle() {
        listBgColorPicker.setValue(Color.WHITE);
        listFontColorPicker.setValue(Color.BLACK);
    }

    /**
     * Resets the fields in this object.
     */
    public void reset() {
        this.board = null;
        text.setText("");
        this.boardBgColorPicker.setValue(Color.WHITE);
        this.boardFontColorPicker.setValue(Color.WHITE);
    }
}
