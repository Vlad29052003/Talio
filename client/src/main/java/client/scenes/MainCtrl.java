package client.scenes;

import client.MyFXML;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import commons.Board;
import commons.Task;
import commons.TaskList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

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
    private Parent boardRoot; // Not a scene as it's to be embedded within the workspaceScene.
    private Node dnd;

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
        this.boardRoot = board.getValue();

        primaryStage.setTitle("Talio");
        primaryStage.setScene(workspaceScene);

        workspaceCtrl.setBoardView(boardRoot);

        primaryStage.show();
    }

    /**
     * Initializes the Scenes and Controllers for the CRUD operations regarding Board.
     *
     * @param joinBoard   is the Scene for joining Boards.
     * @param newBoard    is the Scene for creating a new Board.
     * @param editBoard   is the Scene for editing a Board.
     * @param deleteBoard is the Scene for deleting a Board.
     */
    public void initializeBoardCrud(Pair<JoinBoardCtrl, Parent> joinBoard,
                                    Pair<CreateNewBoardCtrl, Parent> newBoard,
                                    Pair<EditBoardCtrl, Parent> editBoard,
                                    Pair<DeleteBoardCtrl, Parent> deleteBoard) {
        this.joinBoardCtrl = joinBoard.getKey();
        this.joinBoard = new Scene(joinBoard.getValue());

        this.createBoardCtrl = newBoard.getKey();
        this.createBoard = new Scene(newBoard.getValue());

        this.editBoardCtrl = editBoard.getKey();
        this.editBoard = new Scene(editBoard.getValue());

        this.deleteBoardCtrl = deleteBoard.getKey();
        this.deleteBoard = new Scene(deleteBoard.getValue());
    }

    /**
     * Gets the drag and drop node.
     *
     * @return  the drag and drop node.
     */
    public Node getDragAndDropNode() {
        return dnd;
    }

    /**
     * Sets the drag and drop node.
     *
     * @param dnd is the drag and drop node.
     */
    public void setDragAndDropNode(Node dnd) {
        this.dnd = dnd;
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
     * Loads the scenes for the BoardListingCtrl.
     *
     * @param newBoard is the Board associated with them.
     * @return the new BoardListingCtrl.
     */
    public Pair<BoardListingCtrl, Parent> newBoardListingView(Board newBoard) {
        var pair = myFXML.load(BoardListingCtrl.class,
                "client", "scenes", "BoardListing.fxml");
        pair.getKey().setBoard(newBoard);
        return pair;
    }

    /**
     * Loads a {@link TaskListCtrl} instance and view.
     *
     * @param newTaskList is the {@link TaskList} associated with them.
     * @return the new {@link TaskListCtrl} and its view.
     */
    public Pair<TaskListCtrl, Parent> newTaskListView(TaskList newTaskList) {
        var pair = myFXML.load(TaskListCtrl.class,
                "client", "scenes", "TaskListView.fxml");
        pair.getKey().setTaskList(newTaskList);
        return pair;
    }

    /**
     * Loads a {@link TaskCtrl} instance and view.
     *
     * @param newTask is the {@link Task} associated with them.
     * @return the new {@link TaskCtrl} and its view.
     */
    public Pair<TaskCtrl, Parent> newTaskView(Task newTask) {
        var pair = myFXML.load(TaskCtrl.class,
                "client", "scenes", "TaskView.fxml");
        pair.getKey().setTask(newTask);
        return pair;
    }
}
