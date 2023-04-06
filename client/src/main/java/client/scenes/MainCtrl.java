package client.scenes;

import client.MyFXML;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import client.scenes.crud.task.CreateTaskCtrl;
import client.scenes.crud.task.DeleteTaskCtrl;
import client.scenes.crud.task.EditTaskCtrl;
import client.scenes.crud.tasklists.CreateTaskListCtrl;
import client.scenes.crud.tasklists.DeleteTaskListCtrl;
import client.scenes.crud.tasklists.EditTaskListCtrl;
import client.utils.UpdateHandler;
import client.utils.websocket.WebsocketSynchroniser;
import commons.Board;
import commons.Task;
import commons.TaskList;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {
    private Stage primaryStage;
    private Stage popupStage;
    private MyFXML myFXML;
    private WorkspaceCtrl workspaceCtrl;
    private Scene workspaceScene;
    private JoinBoardCtrl joinBoardCtrl;
    private Scene joinBoard;
    private CreateNewBoardCtrl createBoardCtrl;
    private Scene createBoard;
    private CreateTaskListCtrl createListCtrl;
    private Scene createList;
    private EditBoardCtrl editBoardCtrl;
    private Scene editBoard;
    private EditTaskListCtrl editTaskListCtrl;
    private Scene editList;
    private DeleteBoardCtrl deleteBoardCtrl;
    private Scene deleteBoard;
    private DeleteTaskListCtrl deleteListCtrl;
    private Scene deleteList;
    private BoardCtrl boardCtrl;
    private Parent boardRoot; // Not a scene as it's to be embedded within the workspaceScene.
    private Node dnd;
    private WebsocketSynchroniser boardSyncroniser;
    private DeleteTaskCtrl deleteTaskCtrl;
    private Scene deleteTask;
    private CreateTaskCtrl createTaskCtrl;
    private Scene createTask;
    private EditTaskCtrl editTaskCtrl;
    private Scene editTask;
    private HelpScreenCtrl helpScreenCtrl;
    private Scene helpScreen;
    private Task isFocused;

    public Task getIsFocused() {
        return isFocused;
    }

    public void setIsFocused(Task isFocused) {
        this.isFocused = isFocused;
    }

    /**
     * Sets myFXML.
     *
     * @param myFXML is myFXML.
     */
    public void setMyFXML(MyFXML myFXML) {
        this.myFXML = myFXML;
    }

    /**
     * Initializes the primaryStage, WorkspaceScene
     * and initial BoardScene Scenes and Controllers for Workspace and
     * initial Board.
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

        this.popupStage = new Stage();
        popupStage.setResizable(false);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(primaryStage);

        this.workspaceCtrl = workspace.getKey();
        this.workspaceScene = new Scene(workspace.getValue());
        this.boardCtrl = board.getKey();

        primaryStage.setTitle("Talio");
        primaryStage.setScene(workspaceScene);

        workspaceCtrl.setBoardView(board.getValue());

        this.boardSyncroniser = new WebsocketSynchroniser(new MyUpdateHandler());

        // Connect
        workspaceCtrl.fetch();

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
     * Get the boardCtrl.
     *
     * @return the {@link BoardCtrl} instance we're currently rendering
     */
    public BoardCtrl getBoardCtrl() {
        return boardCtrl;
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
     * @param newTaskList    is the Scene for creating a TaskList.
     * @param editTaskList   is the Scene for editing a TaskList.
     */
    public void initializeTaskListCrud(Pair<DeleteTaskListCtrl, Parent> deleteTaskList,
                                       Pair<CreateTaskListCtrl, Parent> newTaskList,
                                       Pair<EditTaskListCtrl, Parent> editTaskList) {

        this.deleteListCtrl = deleteTaskList.getKey();
        this.deleteList = new Scene(deleteTaskList.getValue());

        this.createListCtrl = newTaskList.getKey();
        this.createList = new Scene(newTaskList.getValue());

        this.editTaskListCtrl = editTaskList.getKey();
        this.editList = new Scene(editTaskList.getValue());
    }

    /**
     * Initializes the HelpScreen Scene and Contoller.
     *
     * @param helpScreen is the Scene for the keyboard shortcuts menu.
     */
    public void initializeHelpScreen(Pair<HelpScreenCtrl, Parent> helpScreen){

        this.helpScreenCtrl = helpScreen.getKey();
        this.helpScreen = new Scene(helpScreen.getValue());
    }

    /**
     * Initializes the controllers and scenes for the
     * task crud operations.
     *
     * @param deleteTask is the Scene for deleting a Task.
     * @param newTask    is the Scene for creating a Task.
     * @param editTask   is the Scene for editing a Task.
     */
    public void initializeTaskCrud(Pair<DeleteTaskCtrl, Parent> deleteTask,
                                   Pair<CreateTaskCtrl, Parent> newTask,
                                   Pair<EditTaskCtrl, Parent> editTask) {
        this.deleteTaskCtrl = deleteTask.getKey();
        this.deleteTask = new Scene(deleteTask.getValue());

        this.createTaskCtrl = newTask.getKey();
        this.createTask = new Scene(newTask.getValue());

        this.editTaskCtrl = editTask.getKey();
        this.editTask = new Scene(editTask.getValue());
    }

    /**
     * Gets the drag and drop node.
     *
     * @return the drag and drop node.
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
     * Stops all services depending on MainCtrl
     */
    public void stop() {
        workspaceCtrl.stop();
        this.boardSyncroniser.stop();
    }

    /**
     * Embeds a Board within the WorkspaceScene.
     *
     * @param board is the Board to be displayed.
     */
    public void switchBoard(Board board) {
        if (boardCtrl != null)
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
     * Updates the TaskList with the same id as taskList
     * from the workspace.
     *
     * @param taskList is the taskList to be updated.
     */
    public void updateTaskList(TaskList taskList) {
        boardCtrl.updateTaskList(taskList);
    }

    /**
     * Removes the TaskList with the same id as taskList
     * from the workspace.
     *
     * @param taskList is the taskList to be removed.
     */
    public void removeTaskList(TaskList taskList) {
        boardCtrl.removeTaskListFromBoard(taskList);
    }

    /**
     * Removes a Board from the workspace.
     *
     * @param removed is the Board to be removed;
     */
    public void removeFromWorkspace(Board removed) {
        this.removeFromWorkspace(removed.id);
    }

    /**
     * Removed a Board from the workspace.
     *
     * @param id is the id of the Board to be removed;
     */
    public void removeFromWorkspace(long id) {
        workspaceCtrl.removeFromWorkspace(id);
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
     * Switches to the HelpScreen Scene.
     */
    public void openHelpScreen() {
        popupStage.setTitle("Help Screen");
        popupStage.setScene(helpScreen);
        popupStage.show();
    }

    /**
     * Switches to the JoinBoard Scene.
     */
    public void joinBoard() {
        popupStage.setTitle("Join a board");
        popupStage.setScene(joinBoard);
        popupStage.show();
    }

    /**
     * Switches to the AddBoard Scene.
     */
    public void addBoard() {
        popupStage.setTitle("Create new board");
        popupStage.setScene(createBoard);
        popupStage.show();
    }

    /**
     * Switches to the AddTaskList Scene.
     *
     * @param board
     */
    public void addTaskList(Board board) {
        popupStage.setTitle("Add new list");
        popupStage.setScene(createList);
        createListCtrl.setBoard(board);
        popupStage.show();
    }

    /**
     * Switches to AddTask Scene.
     *
     * @param taskList is the TaskList associated with the scene.
     */
    public void addTask(TaskList taskList) {
        createTaskCtrl.setTaskList(taskList);
        popupStage.setTitle("Create task");
        popupStage.setScene(createTask);
        popupStage.show();
    }

    /**
     * Switches to the EditBoard Scene.
     *
     * @param board is the Board to be edited.
     */
    public void editBoard(Board board) {
        popupStage.setTitle("Rename board");
        popupStage.setScene(editBoard);
        editBoardCtrl.setBoard(board);
        popupStage.show();
    }

    /**
     * Switches to the EditTaskList Scene.
     *
     * @param taskList is the TaskList to be edited.
     */
    public void editTaskList(TaskList taskList) {
        popupStage.setTitle("Rename list");
        popupStage.setScene(editList);
        editTaskListCtrl.setTaskList(taskList);
        popupStage.show();
    }

    /**
     * Switches to the EditTask Scene.
     *
     * @param task is the Task to be edited.
     */
    public void editTask(Task task) {
        editTaskCtrl.setTask(task);
        popupStage.setTitle("Edit task");
        popupStage.setScene(editTask);
        popupStage.show();
    }

    /**
     * Switches to the DeleteBoard Scene.
     *
     * @param board is the Board to be deleted.
     */
    public void deleteBoard(Board board) {
        popupStage.setTitle("Delete board");
        popupStage.setScene(deleteBoard);
        deleteBoardCtrl.setBoard(board);
        popupStage.show();
    }

    /**
     * Switches to the DeleteTaskList Scene.
     *
     * @param taskList is the TaskList to be deleted.
     */
    public void deleteTaskList(TaskList taskList) {
        popupStage.setTitle("Delete list");
        popupStage.setScene(deleteList);
        deleteListCtrl.setTaskList(taskList);
        popupStage.show();

    }

    /**
     * Switches to the DeleteTask Scene.
     *
     * @param task is the Task to be deleted.
     */
    public void deleteTask(Task task) {
        deleteTaskCtrl.setTask(task);
        popupStage.setTitle("Delete task");
        popupStage.setScene(deleteTask);
        popupStage.show();
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
        return workspaceCtrl.getBoards().stream().anyMatch(w -> w.getBoard().id == board.id);
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
     * Gets the board that is currently being displayed.
     *
     * @return the board.
     */
    public Board getActiveBoard() {
        return boardCtrl.getBoard();
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
     * Refreshes the Board.
     *
     * @param board is the new board.
     */
    public void refreshBoard(Board board) {
        boardCtrl.setBoard(board);
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

    /**
     * Refresh this view.
     */
    public void refresh() {
        this.boardCtrl.refresh();
    }

    /**
     * Updates the task in the ListView.
     *
     * @param task is the updated Task.
     */
    public void updateTaskInList(Task task) {
        try {
            boardCtrl.updateTask(task);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the task from ListView.
     *
     * @param task is the Task to be removed.
     */
    public void removeTask(Task task) {
        boardCtrl.removeTask(task);
    }

    public void resetFocus() {
        boardCtrl.resetFocus();
    }

    public void getNextIndex(TaskList taskList, int index) {
        boardCtrl.getNextIndex(taskList, index);
    }

    public void getNeighbourIndex(TaskList taskList, int index, boolean isRight) {
        boardCtrl.getNeighbourIndex(taskList, index, isRight);
    }

    public class MyUpdateHandler extends UpdateHandler {

        @Override
        public void onBoardCreated(Board board) {
        }

        @Override
        public void onBoardDeleted(long id) {
            Platform.runLater(() -> removeFromWorkspace(id));
        }

        @Override
        public void onBoardUpdated(Board board) {
            Platform.runLater(() -> updateBoard(board));
        }

        @Override
        public void onDisconnect() {
            workspaceCtrl.tryConnect(workspaceCtrl.getServer());
        }
    }

    /**
     * @return The {@link WebsocketSynchroniser} associated with this {@link MainCtrl}
     */
    public WebsocketSynchroniser getWebsocketSynchroniser(){
        return boardSyncroniser;
    }

    /**
     * Shows the popup Stage
     * when it is in use.
     */
    public void showPopup(){
        popupStage.show();
    }

    /**
     * Hides the popup Stage when
     * it is not in use.
     */
    public void hidePopup(){
        popupStage.hide();
    }
}
