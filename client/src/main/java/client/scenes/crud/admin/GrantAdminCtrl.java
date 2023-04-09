package client.scenes.crud.admin;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
     * Bond to the Cancel button.
     * Switches back to the workspace Scene.
     */
    public void cancel() {
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
