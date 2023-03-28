package client.scenes.crud.board;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;

public class YouHavePermissionCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;

    /**
     * Creates a new {@link YouHavePermissionCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public YouHavePermissionCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Bond to the Ok button.
     * Switches back to the workspace Scene.
     */
    public void ok() {
        mainCtrl.cancel();
    }
}
