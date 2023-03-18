package client;

import static com.google.inject.Guice.createInjector;

import client.scenes.BoardCtrl;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import client.scenes.crud.CreateNewBoardCtrl;
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
        var createBoard = FXML.load(CreateNewBoardCtrl.class, "client", "scenes", "crud", "CreateNewBoard.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, workspace, board, createBoard);
    }

    /**
     * Getter for the FXML field.
     *
     * @return FXML
     */
    public static MyFXML getMyFXML() {
        return FXML;
    }
}