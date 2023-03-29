package client.scenes;

import client.MyFXML;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import client.scenes.crud.tasklists.CreateNewListCtrl;
import client.scenes.crud.tasklists.DeleteListCtrl;
import client.scenes.crud.tasklists.EditListCtrl;
import commons.Board;
import commons.TaskList;
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
    private CreateNewListCtrl createListCtrl;
    private Scene createList;
    private EditBoardCtrl editBoardCtrl;
    private Scene editBoard;
    private EditListCtrl editListCtrl;
    private Scene editList;
    private DeleteBoardCtrl deleteBoardCtrl;
    private Scene deleteBoard;
    private DeleteListCtrl deleteListCtrl;
    private Scene deleteList;
    private BoardCtrl boardCtrl;

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
     * Initializes the Scenes and Controllers for the CRUD operations regarding TaskList.
     *
     * @param deleteTaskList is the Scene for deleting a TaskList.
     * @param newTaskList is the Scene for creating a TaskList.
     * @param editTaskList is the Scene for editing a TaskList.
     */
    public void initializeTaskListCrud(Pair<DeleteListCtrl, Parent> deleteTaskList,
                                       Pair<CreateNewListCtrl, Parent> newTaskList,
                                       Pair<EditListCtrl, Parent> editTaskList) {

        this.deleteListCtrl = deleteTaskList.getKey();
        this.deleteList = new Scene(deleteTaskList.getValue());

        this.createListCtrl = newTaskList.getKey();
        this.createList = new Scene(newTaskList.getValue());

        this.editListCtrl = editTaskList.getKey();
        this.editList = new Scene(editTaskList.getValue());
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
     * Removes a TaskList from a Board.
     *
     * @param removed is the TaskList to be removed.
     */
    public void removeTaskListFromBoard(TaskList removed) {
        boardCtrl.removeTaskListFromBoard(removed);
        //boardCtrl.setTaskList(null);
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
     * Switches to the AddTaskList Scene.
     * @param board
     */
    public void addTaskList(Board board) {
        primaryStage.setScene(createList);
        createListCtrl.setBoard(board);
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
     * Switches to the EditTaskList Scene.
     *
     * @param taskList is the TaskList to be edited.
     */
    public void editTaskList(TaskList taskList) {
        primaryStage.setScene(editList);
        editListCtrl.setTaskList(taskList);
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
     * Switches to the DeleteTaskList Scene.
     *
     * @param taskList is the TaskList to be deleted.
     */
    public void deleteTaskList(TaskList taskList) {
        primaryStage.setScene(deleteList);
        deleteListCtrl.setTaskList(taskList);
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
     * Updates the TaskList with the same id as taskList
     * from the workspace.
     *
     * @param taskList is the taskList to be updated.
     */
    public void updateTaskList(TaskList taskList) {
        boardCtrl.updateTaskList(taskList);
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
     * Adds a TaskList to the board.
     *
     * @param taskList is the TaskList to be added.
     */
    public void addTaskListToBoard(TaskList taskList) {
        if (taskList != null) {
            boardCtrl.addTaskListToBoard(taskList);
        }
        createListCtrl.reset();
        boardCtrl.refresh();
        primaryStage.setScene(workspaceScene);
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
     * Loads a TaskListController for the TaskList
     * to be added to the Board
     *
     * @param tasklist the TaskList to be added
     * @return TaskListController for the TaskList
     */
    public TaskListController loadTaskListController(TaskList tasklist) {
        TaskListController taskListDisplay =
                myFXML.load(TaskListController.class, "client", "scenes",
                        "TaskList.fxml").getKey();
        taskListDisplay.setTasklist(tasklist);
        return taskListDisplay;
    }

    public void refreshBoard(Board board){
        boardCtrl.setBoard(board);
        boardCtrl.refresh();
    }
}
