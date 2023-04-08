package client;

import static com.google.inject.Guice.createInjector;

import client.scenes.BoardCtrl;
import client.scenes.HelpScreenCtrl;
import client.scenes.MainCtrl;
import client.scenes.TagOverviewCtrl;
import client.scenes.WorkspaceCtrl;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import client.scenes.crud.tag.CreateTagCtrl;
import client.scenes.crud.tag.DeleteTagCtrl;
import client.scenes.crud.tag.EditTagCtrl;
import client.scenes.crud.task.CreateTaskCtrl;
import client.scenes.crud.task.DeleteTaskCtrl;
import client.scenes.crud.task.EditTaskCtrl;
import client.scenes.crud.task.OpenTaskCtrl;
import client.scenes.crud.tasklists.CreateTaskListCtrl;
import client.scenes.crud.tasklists.DeleteTaskListCtrl;
import client.scenes.crud.tasklists.EditTaskListCtrl;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    private MainCtrl mainCtrl;

    /**
     * The main function. Immediately hands over control to JavaFX.
     *
     * @param args an array of command line arguments given
     */
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        this.mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.setMyFXML(FXML);

        var workspace = FXML.load(WorkspaceCtrl.class,
                "client", "scenes", "WorkspaceView.fxml");
        var board = FXML.load(BoardCtrl.class,
                "client", "scenes", "BoardView.fxml");
        var joinBoard = FXML.load(JoinBoardCtrl.class,
                "client", "scenes", "crud", "JoinBoard.fxml");
        var createBoard = FXML.load(CreateNewBoardCtrl.class,
                "client", "scenes", "crud", "CreateNewBoard.fxml");
        var editBoard = FXML.load(EditBoardCtrl.class,
                "client", "scenes", "crud", "EditBoard.fxml");
        var deleteBoard = FXML.load(DeleteBoardCtrl.class,
                "client", "scenes", "crud", "ConfirmBoardDelete.fxml");
        var deleteTaskList = FXML.load(DeleteTaskListCtrl.class,
                "client", "scenes", "crud", "ConfirmListDelete.fxml");
        var newTaskList = FXML.load(CreateTaskListCtrl.class,
                "client", "scenes", "crud", "CreateNewList.fxml");
        var editTaskList = FXML.load(EditTaskListCtrl.class,
                "client", "scenes", "crud", "EditListName.fxml");
        var deleteTask = FXML.load(DeleteTaskCtrl.class,
                "client", "scenes", "crud", "ConfirmTaskDelete.fxml");
        var newTask = FXML.load(CreateTaskCtrl.class,
                "client", "scenes", "crud", "CreateNewTask.fxml");
        var editTask = FXML.load(EditTaskCtrl.class,
                "client", "scenes", "crud", "EditTask.fxml");
        var helpScreen = FXML.load(HelpScreenCtrl.class,
                "client", "scenes", "HelpScreen.fxml");
        var deleteTag = FXML.load(DeleteTagCtrl.class,
                "client", "scenes", "crud", "ConfirmTagDelete.fxml");
        var newTag = FXML.load(CreateTagCtrl.class,
                "client", "scenes", "crud", "CreateNewTag.fxml");
        var editTag = FXML.load(EditTagCtrl.class,
                "client", "scenes", "crud", "EditTagName.fxml");
        var tagOverview = FXML.load(TagOverviewCtrl.class,
                "client", "scenes", "TagOverview.fxml");
        var openTask = FXML.load(OpenTaskCtrl.class,
                "client", "scenes", "crud", "OpenTask.fxml");

        mainCtrl.initialize(primaryStage, workspace, board, tagOverview);
        mainCtrl.initializeBoardCrud(joinBoard, createBoard, editBoard, deleteBoard);
        mainCtrl.initializeTaskListCrud(deleteTaskList, newTaskList, editTaskList);
        mainCtrl.initializeTaskCrud(deleteTask, newTask, editTask, openTask);
        mainCtrl.initializeHelpScreen(helpScreen);
        mainCtrl.initializeTagCrud(deleteTag, newTag, editTag);

        primaryStage.setOnCloseRequest(e -> mainCtrl.stop());
    }
}