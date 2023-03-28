package client;

import static com.google.inject.Guice.createInjector;

import client.scenes.BoardCtrl;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import client.scenes.crud.tasklists.CreateNewListCtrl;
import client.scenes.crud.tasklists.DeleteListCtrl;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        var workspace = FXML.load(WorkspaceCtrl.class, "client", "scenes", "WorkspaceView.fxml");
        var board = FXML.load(BoardCtrl.class, "client", "scenes", "BoardView.fxml");

        var joinBoard = FXML
                .load(JoinBoardCtrl.class, "client", "scenes", "crud", "JoinBoard.fxml");
        var createBoard = FXML
                .load(CreateNewBoardCtrl.class, "client", "scenes", "crud", "CreateNewBoard.fxml");
        var editBoard = FXML
                .load(EditBoardCtrl.class, "client", "scenes", "crud", "EditBoardName.fxml");
        var deleteBoard = FXML
                .load(DeleteBoardCtrl.class, "client", "scenes", "crud", "ConfirmBoardDelete.fxml");
        var deleteTaskList = FXML
                .load(DeleteListCtrl.class, "client", "scenes", "crud", "ConfirmListDelete.fxml");
        var newTaskList = FXML
                .load(CreateNewListCtrl.class, "client", "scenes", "crud", "CreateNewList.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);

        mainCtrl.initialize(primaryStage, FXML, workspace, board);
        mainCtrl.initializeBoardCrud(joinBoard, createBoard, editBoard, deleteBoard);
        mainCtrl.initializeTaskListCrud(deleteTaskList, newTaskList);
    }
}