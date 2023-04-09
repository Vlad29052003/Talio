package client.scenes;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;

public class HelpScreenCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;

    /**
     * Creates a new {@link HelpScreenCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public HelpScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Bound to the Close button.
     * Switches back to the workspace Scene.
     */
    public void cancel() {
        mainCtrl.hidePopup();
    }
}
