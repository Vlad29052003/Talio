package client.scenes.crud.admin;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.List;

public class GrantAdminCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    @FXML
    private TextField text;

    /**
     * Creates a new {@link GrantAdminCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public GrantAdminCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
     * Bond to the Cancel button.
     * Switches back to the workspace Scene.
     */
    public void cancel() {
        Platform.runLater(() -> text.requestFocus());
        reset();
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }

    /**
     * Bound to the Confirm button.
     * Sends a request to the server
     * to test the password.
     */
    public void confirm(){
        Platform.runLater(() -> text.requestFocus());
        if(text.getText().equals(server.getPassword())){
            mainCtrl.setAdminTrue();
            List<Board> list = server.addAllBoards();
            mainCtrl.addBoardListToWorkspace(list);
            mainCtrl.hidePopup();
            mainCtrl.permissionAdmin();
        }else{
            reset();
            mainCtrl.hidePopup();
            mainCtrl.accessDenied();
        }
    }

    /**
     * Resets the fields in this object.
     */
    private void reset() {
        text.setText("");
    }

}
