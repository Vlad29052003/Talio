package client;

import static com.google.inject.Guice.createInjector;

import client.scenes.BoardCtrl;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import client.scenes.crud.admin.AccessDeniedCtrl;
import client.scenes.crud.admin.GrantAdminCtrl;
import client.scenes.crud.admin.PermissionAdminCtrl;
import client.scenes.crud.admin.ReadWritePasswordCtrl;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import client.scenes.crud.board.ReadWritePermissionsCtrl;
import client.scenes.crud.board.YouHavePermissionCtrl;
import com.google.inject.Injector;
import commons.Password;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static Password password = new Password();
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
        var grantAdmin = FXML
                .load(GrantAdminCtrl.class, "client", "scenes", "crud", "GrantAdmin.fxml");
        var accessDenied = FXML
                .load(AccessDeniedCtrl.class,"client", "scenes", "crud", "AccessDenied.fxml");
        var permissionAdmin = FXML
                .load(PermissionAdminCtrl.class,"client", "scenes", "crud", "PermissionAdmin.fxml");
        var readWritePermissions = FXML
                .load(ReadWritePermissionsCtrl.class,"client", "scenes", "crud",
                                                        "ReadWritePermissions.fxml");
        var youHavePermission = FXML
                .load(YouHavePermissionCtrl.class,"client", "scenes", "crud",
                                                        "YouHavePermission.fxml");
        var readWritePassord = FXML
                .load(ReadWritePasswordCtrl.class, "client", "scenes", "crud",
                                                        "ReadWritePassword.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);

        mainCtrl.initialize(primaryStage, FXML, workspace, board);
        mainCtrl.initializeBoardCrud(joinBoard, createBoard, editBoard, deleteBoard,
                                        readWritePermissions, youHavePermission);
        mainCtrl.initializeAdminCrud(grantAdmin, accessDenied, permissionAdmin, readWritePassord);

    }
}