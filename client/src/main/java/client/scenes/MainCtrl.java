package client.scenes;

import client.MyFXML;
import client.scenes.crud.admin.AccessDeniedCtrl;
import client.scenes.crud.admin.GrantAdminCtrl;
import client.scenes.crud.admin.PermissionAdminCtrl;
import client.scenes.crud.admin.EditBoardPasswordCtrl;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import client.scenes.crud.board.EditPermissionCtrl;
import client.scenes.crud.board.YouHavePermissionCtrl;
import commons.Board;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;

public class MainCtrl {
    private Stage primaryStage;
    private MyFXML myFXML;
    private WorkspaceCtrl workspaceCtrl;
    private Scene workspaceScene;
    private JoinBoardCtrl joinBoardCtrl;
    private Scene joinBoard;
    private CreateNewBoardCtrl createBoardCtrl;
    private Scene createBoard;
    private EditBoardCtrl editBoardCtrl;
    private Scene editBoard;
    private DeleteBoardCtrl deleteBoardCtrl;
    private Scene deleteBoard;
    private BoardCtrl boardCtrl;
    private Scene grantAdmin;
    private GrantAdminCtrl grantAdminCtrl;
    private Scene accessDenied;
    private AccessDeniedCtrl accessDeniedCtrl;
    private Scene permissionAdmin;
    private PermissionAdminCtrl permissionAdminCtrl;
    private Scene editPermission;
    private EditPermissionCtrl editPermissionCtrl;
    private Scene  youHavePermission;
    private YouHavePermissionCtrl youHavePermissionCtrl;
    private Scene editBoardPassword;
    private EditBoardPasswordCtrl editBoardPasswordCtrl;

    private boolean admin = false;

    /**
     * Initializes the primaryStage, WorkspaceScene
     * and initial BoardScene Scenes and Controllers for Workspace and
     * initial Board.
     *
     * @param primaryStage is the primary Stage.
     * @param myFXML       is the FXML loader
     * @param workspace    is the Workspace.
     * @param board        is the initial Board, which is empty.
     */
    public void initialize(
            Stage primaryStage,
            MyFXML myFXML,
            Pair<WorkspaceCtrl, Parent> workspace,
            Pair<BoardCtrl, Parent> board) {
        this.primaryStage = primaryStage;

        this.myFXML = myFXML;

        this.workspaceCtrl = workspace.getKey();
        this.workspaceScene = new Scene(workspace.getValue());
        this.boardCtrl = board.getKey();

        primaryStage.setTitle("Talio");
        primaryStage.setScene(workspaceScene);

        workspaceCtrl.setBoardView(board.getValue());

        primaryStage.show();
    }

    /**
     * Sets the workspaceCtrl. Used for testing.
     *
     * @param workspaceCtrl is the WorkspaceCtrl
     */
    public void setWorkspaceCtrl(WorkspaceCtrl workspaceCtrl) {
        this.workspaceCtrl = workspaceCtrl;
    }

    /**
     * Sets the boardCtrl. Used for testing.
     *
     * @param boardCtrl is the BoardCtrl
     */
    public void setBoardCtrl(BoardCtrl boardCtrl) {
        this.boardCtrl = boardCtrl;
    }

    /**
     * Initializes the Scenes and Controllers for the CRUD operations regarding Board.
     *
     * @param joinBoard   is the Scene for joining Boards.
     * @param newBoard    is the Scene for creating a new Board.
     * @param editBoard   is the Scene for editing a Board.
     * @param deleteBoard is the Scene for deleting a Board.
     * @param editPermission is the Scene for inputting the password of the board.
     * @param youHavePermission is the Scene for the situation in which you already have permission.
     */
    public void initializeBoardCrud(Pair<JoinBoardCtrl, Parent> joinBoard,
                                    Pair<CreateNewBoardCtrl, Parent> newBoard,
                                    Pair<EditBoardCtrl, Parent> editBoard,
                                    Pair<DeleteBoardCtrl, Parent> deleteBoard,
                                    Pair<EditPermissionCtrl, Parent> editPermission,
                                    Pair<YouHavePermissionCtrl, Parent> youHavePermission) {
        this.joinBoardCtrl = joinBoard.getKey();
        this.joinBoard = new Scene(joinBoard.getValue());

        this.createBoardCtrl = newBoard.getKey();
        this.createBoard = new Scene(newBoard.getValue());

        this.editBoardCtrl = editBoard.getKey();
        this.editBoard = new Scene(editBoard.getValue());

        this.deleteBoardCtrl = deleteBoard.getKey();
        this.deleteBoard = new Scene(deleteBoard.getValue());

        this.editPermissionCtrl = editPermission.getKey();
        this.editPermission = new Scene(editPermission.getValue());

        this.youHavePermissionCtrl = youHavePermission.getKey();
        this.youHavePermission = new Scene(youHavePermission.getValue());
    }

    /**
     * Initializes the Scenes and Controllers for the CRUD operations regarding admin operations.
     *
     * @param grantAdmin is the Scene for granting admin.
     * @param accessDenied is the scene for not having the administrator permission.
     * @param permissionAdmin is the scene for introducing the correct password.
     * @param editBoardPassword is the scene for the board edit permission.
     */

    public void initializeAdminCrud(Pair<GrantAdminCtrl, Parent> grantAdmin,
                                    Pair<AccessDeniedCtrl, Parent> accessDenied,
                                    Pair<PermissionAdminCtrl, Parent> permissionAdmin,
                                    Pair<EditBoardPasswordCtrl, Parent> editBoardPassword) {

        this.grantAdminCtrl = grantAdmin.getKey();
        this.grantAdmin = new Scene(grantAdmin.getValue());

        this.accessDeniedCtrl = accessDenied.getKey();
        this.accessDenied = new Scene(accessDenied.getValue());

        this.permissionAdminCtrl = permissionAdmin.getKey();
        this.permissionAdmin = new Scene(permissionAdmin.getValue());

        this.editBoardPasswordCtrl = editBoardPassword.getKey();
        this.editBoardPassword = new Scene(editBoardPassword.getValue());
    }

    /**
     * Embeds a Board within the WorkspaceScene.
     *
     * @param board is the Board to be displayed.
     */
    public void switchBoard(Board board) {
        boardCtrl.setBoard(board);
    }

    /**
     * Removes a BoardListingCtrl from the workspace.
     *
     * @param removed is the BoardListingCtrl to be removed.
     */
    public void removeFromWorkspace(BoardListingCtrl removed) {
        workspaceCtrl.removeFromWorkspace(removed);
        boardCtrl.setBoard(null);
    }

    /**
     * Removed a Board from the workspace.
     *
     * @param removed is the Board to be removed;
     */
    public void removeFromWorkspace(Board removed) {
        workspaceCtrl.removeFromWorkspace(removed);
        boardCtrl.setBoard(null);
    }

    /**
     * Switches back to the previous WorkspaceScene.
     */
    public void cancel() {
        if (createBoardCtrl.getBoard() != null) {
            workspaceCtrl.addBoardToWorkspace(createBoardCtrl.getBoard());
        }
        createBoardCtrl.reset();
        primaryStage.setScene(workspaceScene);
    }

    /**
     * Switches to the JoinBoard Scene.
     */
    public void joinBoard() {
        primaryStage.setScene(joinBoard);
    }

    /**
     * Switches to the AddBoard Scene.
     */
    public void addBoard() {
        primaryStage.setScene(createBoard);
    }

    /**
     * Switches to the EditBoard Scene.
     *
     * @param board is the Board to be edited.
     */
    public void editBoard(Board board) {
        primaryStage.setScene(editBoard);
        editBoardCtrl.setBoard(board);
    }

    /**
     * Switches to the DeleteBoard Scene.
     *
     * @param board is the Board to be deleted.
     */
    public void deleteBoard(Board board) {
        primaryStage.setScene(deleteBoard);
        deleteBoardCtrl.setBoard(board);
    }

    /**
     * Updates the Board with the same id as board
     * from the workspace.
     *
     * @param board is the Board to be updated.
     */
    public void updateBoard(Board board) {
        workspaceCtrl.updateBoard(board);
        if (boardCtrl.getBoard() != null && boardCtrl.getBoard().id == board.id)
            boardCtrl.setBoard(board);
    }

    /**
     * Checks if the Board is already in the Workspace.
     *
     * @param board the Board.
     * @return true if present, false otherwise.
     */
    public boolean isPresent(Board board) {
        return workspaceCtrl.getBoards().stream().anyMatch(w -> w.getBoard().equals(board));
    }

    /**
     * Adds a Board to the workspace.
     *
     * @param board is the Board to be added.
     */
    public void addBoardToWorkspace(Board board) {
        if (board != null) {
            workspaceCtrl.addBoardToWorkspace(board);
        }
        createBoardCtrl.reset();
        joinBoardCtrl.reset();
        primaryStage.setScene(workspaceScene);
    }

    /**
     * Adds a List of Board to the workspace.
     *
     * @param list is the list of Boards to be added.
     */
    public void addBoardListToWorkspace(List<Board> list) {
        for (Board board : list) {
            if (board != null) {
                if(!isPresent(board)) {
                    workspaceCtrl.addBoardToWorkspace(board);
                }
            }
        }
    }
    /**
     * Loads the scenes for the BoardListingCtrl.
     *
     * @param newBoard is the Board associated with them.
     * @return the new BoardListingCtrl.
     */
    public Pair<BoardListingCtrl, Parent> newBoardListingView(Board newBoard) {
        var pair =
                myFXML.load(BoardListingCtrl.class, "client", "scenes",
                        "BoardListing.fxml");
        pair.getKey().setBoard(newBoard);
        return pair;
    }

    /**
     * Loads the scene for GrantAdmin.
     *
     */
    public void grantAdmin() {
        primaryStage.setScene(grantAdmin);
    }

    /**
     * Loads the scene for AccessDenied.
     *
     */
    public void accessDenied(){
        primaryStage.setScene(accessDenied);
    }

    /**
     * Loads the scene for Permision Admin.
     *
     */
    public void permissionAdmin(){
        primaryStage.setScene(permissionAdmin);
    }

    /**
     * Loads the scene for Read / Write permissions.
     * @param board for which we check the password.
     */
    public void editPermission(Board board){
        editPermissionCtrl.setBoard(board);
        primaryStage.setScene(editPermission);
    }

    /**
     * Loads the scene You have permission.
     */
    public void youHavePermission() {
        primaryStage.setScene(youHavePermission);
    }

    /**
     * Edits the board password.
     * @param board the board that is edited.
     */
    public void editBoardPassword(Board board) {
        editBoardPasswordCtrl.setBoard(board);
        primaryStage.setScene(editBoardPassword);
    }

    /**
     * Getter for the admin permission.
     *
     * @return admin.
     */
    public boolean getAdmin(){
        return admin;
    }

    /**
     * Sets admin to true.
     *
     */
    public void setAdminTrue(){
        this.admin = true;
    }
}
