package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {
    private Stage primaryStage;

    private WorkspaceCtrl workspaceCtrl;
    private Scene workspaceScene;

    private BoardCtrl boardCtrl;
    private BoardCtrl noBoardCtrl;
    private Parent boardRoot; // Not a scene as it's to be embedded within the workspaceScene.

    /**
     * Initializes the application.
     *
     * @param primaryStage is the primary Stage.
     * @param workspace    is the Workspace.
     * @param board        is the initial Board, which is empty.
     */
    public void initialize(
            Stage primaryStage,
            Pair<WorkspaceCtrl, Parent> workspace,
            Pair<BoardCtrl, Parent> board) {
        this.primaryStage = primaryStage;

        this.workspaceCtrl = workspace.getKey();
        this.workspaceScene = new Scene(workspace.getValue());
        this.boardCtrl = board.getKey();
        this.boardRoot = board.getValue();
        this.noBoardCtrl = boardCtrl;

        primaryStage.setTitle("Talio");
        primaryStage.setScene(workspaceScene);

        workspaceCtrl.setBoardView(boardRoot);

        primaryStage.show();
    }

    /**
     * Embeds a Board within the WorkspaceScene.
     *
     * @param newBoardCtrl is the BoardCtrl to be embedded.
     */
    public void switchBoard(BoardCtrl newBoardCtrl) {
        this.boardCtrl = newBoardCtrl;
        workspaceCtrl.setBoardView(newBoardCtrl.getBoardView());
        newBoardCtrl.refresh();
    }

    /**
     * Removes a BoardDisplayWorkspace from the workspace
     *
     * @param boardDisplayWorkspace is the BoardDisplayWorkspace to be removed
     */
    public void removeFromWorkspace(BoardDisplayWorkspace boardDisplayWorkspace) {
        workspaceCtrl.removeFromWorkspace(boardDisplayWorkspace);
        switchBoard(noBoardCtrl);
    }
}
