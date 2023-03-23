package scenes;

import client.scenes.BoardCtrl;
import client.scenes.BoardDisplayWorkspace;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import java.util.ArrayList;
import java.util.List;

public class MainCtrlTesting extends MainCtrl {
    private List<String> calledMethods;
    private WorkspaceCtrl workspaceCtrl;

    /**
     * Create a new {@link MainCtrlTesting} instance.
     */
    public MainCtrlTesting() {
        calledMethods = new ArrayList<>();
    }

    /**
     * Dummy function for switching currently active board.
     * @param newBoardCtrl is the BoardCtrl to be embedded.
     */
    public void switchBoard(BoardCtrl newBoardCtrl) {
        calledMethods.add("switchBoard");
        workspaceCtrl.setBoardView(newBoardCtrl.getBoardView());
    }

    /**
     * Removes a BoardDisplayWorkspace from the workspace
     *
     * @param boardDisplayWorkspace is the BoardDisplayWorkspace to be removed
     */
    public void removeFromWorkspace(BoardDisplayWorkspace boardDisplayWorkspace) {
        calledMethods.add("removeFromWorkspace");
        workspaceCtrl.removeFromWorkspace(boardDisplayWorkspace);
    }
}
