package client.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.IOException;

public class MainCtrl {
    private Stage primaryStage;

    private WorkspaceCtrl workspaceCtrl;
    private Scene workspaceScene;

    private BoardCtrl boardCtrl;
    private Parent boardRoot; // Not a scene as it's to be embedded within the workspaceScene.

    public void initialize(
            Stage primaryStage,
            Pair<WorkspaceCtrl, Parent> workspace,
            Pair<BoardCtrl, Parent> board
    ) {
        this.primaryStage = primaryStage;

        this.workspaceCtrl = workspace.getKey();
        this.workspaceScene = new Scene(workspace.getValue());
        this.boardCtrl = board.getKey();
        this.boardRoot = board.getValue();

        primaryStage.setTitle("Talio");
        primaryStage.setScene(workspaceScene);

        workspaceCtrl.setBoardView(boardRoot);

        primaryStage.show();
    }

    public void switchBoard(BoardCtrl newBoard){

        boardCtrl.refresh();
    }


}
