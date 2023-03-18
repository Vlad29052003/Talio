package scenes;

import client.scenes.BoardCtrl;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import java.util.ArrayList;
import java.util.List;

public class MainCtrlTesting extends MainCtrl {
    private List<String> calledMethods;
    private WorkspaceCtrl workspaceCtrl;

    public MainCtrlTesting() {
        calledMethods = new ArrayList<>();
    }


    public void switchBoard(BoardCtrl newBoardCtrl) {

        calledMethods.add("switchBoard");
        workspaceCtrl.setBoardView(newBoardCtrl.getBoardView());
    }
}
