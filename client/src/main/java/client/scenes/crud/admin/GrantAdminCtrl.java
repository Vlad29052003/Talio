package client.scenes.crud.admin;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static commons.Password.checkPassword;
import static commons.Password.getAdmin;

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
        mainCtrl.cancel();
    }

    /**
     * Bound to the Confirm button.
     * Sends a request to the server
     * to test the password.
     */
    public void confirm(){
        checkPassword(text.getText());
        if(getAdmin()){
            mainCtrl.permissionAdmin();
        }else{
            mainCtrl.accessDenied();
        }
    }

}
