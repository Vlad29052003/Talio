package client;

import static com.google.inject.Guice.createInjector;

import client.scenes.BoardCtrl;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import client.scenes.crud.tasklists.CreateTaskListCtrl;
import client.scenes.crud.tasklists.DeleteTaskListCtrl;
import client.scenes.crud.tasklists.EditTaskListCtrl;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * The main function. Immediately hands over control to JavaFX.
     * @param args an array of command line arguments given
     */
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        var workspace = FXML.load(
                WorkspaceCtrl.class,
                "client", "scenes", "WorkspaceView.fxml"
        );
        var board = FXML.load(
                BoardCtrl.class,
                "client", "scenes", "BoardView.fxml"
        );

        var joinBoard = FXML.load(
                JoinBoardCtrl.class,
                "client", "scenes", "crud", "JoinBoard.fxml"
        );
        var createBoard = FXML.load(
                CreateNewBoardCtrl.class,
                "client", "scenes", "crud", "CreateNewBoard.fxml"
        );
        var editBoard = FXML.load(
                EditBoardCtrl.class,
                "client", "scenes", "crud", "EditBoardName.fxml"
        );
        var deleteBoard = FXML.load(
                DeleteBoardCtrl.class,
                "client", "scenes", "crud", "ConfirmBoardDelete.fxml"
        );
        var deleteTaskList = FXML.load(
                DeleteTaskListCtrl.class,
                "client", "scenes", "crud", "ConfirmListDelete.fxml"
        );
        var newTaskList = FXML.load(
                CreateTaskListCtrl.class,
                "client", "scenes", "crud", "CreateNewList.fxml"
        );
        var editTaskList = FXML.load(
                EditTaskListCtrl.class,
                "client", "scenes", "crud", "EditListName.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);

        mainCtrl.initialize(primaryStage, FXML, workspace, board);
        mainCtrl.initializeBoardCrud(joinBoard, createBoard, editBoard, deleteBoard);
        mainCtrl.initializeTaskListCrud(deleteTaskList, newTaskList, editTaskList);
    }
}