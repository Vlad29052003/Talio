package client.scenes.crud.admin;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import java.util.List;

public class PermissionAdminCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;

    /**
     * Creates a new {@link PermissionAdminCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public PermissionAdminCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Bound to the Ok button.
     * Switches back to the workspace Scene.
     */
    public void ok() {
        List<Board> list = server.addAllBoards();
        mainCtrl.addBoardListToWorkspace(list);
        mainCtrl.cancel();
        mainCtrl.hidePopup();
    }
}
