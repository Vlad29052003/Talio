package client.scenes;

import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import commons.Board;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {
    private Stage primaryStage;

    private WorkspaceCtrl workspaceCtrl;
    private Scene workspaceScene;
    private CreateNewBoardCtrl createBoardCtrl;
    private Scene createBoard;
    private EditBoardCtrl editBoardCtrl;
    private Scene editBoard;
    private DeleteBoardCtrl deleteBoardCtrl;
    private Scene deleteBoard;
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
            Pair<BoardCtrl, Parent> board,
            Pair<CreateNewBoardCtrl, Parent> newBoard,
            Pair<EditBoardCtrl, Parent> editBoard,
            Pair<DeleteBoardCtrl, Parent> deleteBoard) {
        this.primaryStage = primaryStage;

        this.workspaceCtrl = workspace.getKey();
        this.workspaceScene = new Scene(workspace.getValue());
        this.boardCtrl = board.getKey();
        this.boardRoot = board.getValue();
        this.noBoardCtrl = boardCtrl;

        this.createBoardCtrl = newBoard.getKey();
        this.createBoard = new Scene(newBoard.getValue());

        this.editBoardCtrl = editBoard.getKey();
        this.editBoard = new Scene(editBoard.getValue());

        this.deleteBoardCtrl = deleteBoard.getKey();
        this.deleteBoard = new Scene(deleteBoard.getValue());

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

    public void removeFromWorkspace(Board removed) {
        workspaceCtrl.removeFromWorkspace(removed);
        switchBoard(noBoardCtrl);
    }

    public void cancel() {
        if(createBoardCtrl.getBoard() != null) {
            workspaceCtrl.addBoardToWorkspace(createBoardCtrl.getBoard());
        }
        createBoardCtrl.reset();
        primaryStage.setScene(workspaceScene);
    }

    public void addBoard() {
        primaryStage.setScene(createBoard);
    }

    public void editBoard(Board board) {
        primaryStage.setScene(editBoard);
        editBoardCtrl.setBoard(board);
    }

    public void deleteBoard(Board board) {
        primaryStage.setScene(deleteBoard);
        deleteBoardCtrl.setBoard(board);
    }

    public void updateBoard(Board board) {
        workspaceCtrl.updateBoard(board);
    }
}
