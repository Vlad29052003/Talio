package scenes;

import client.scenes.BoardCtrl;
import client.scenes.BoardDisplayWorkspace;
import client.scenes.WorkspaceCtrl;
import commons.Board;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestWorkspaceCtrl {
    private WorkspaceCtrl workspaceCtrl;
    private MainCtrlTesting mainCtrl;
    private ServerUtilsTesting server;

    @BeforeEach
    public void setUp() {
        this.mainCtrl = new MainCtrlTesting();
        this.server = new ServerUtilsTesting();
        mainCtrl.setServer(server);
        workspaceCtrl = new WorkspaceCtrl(server, mainCtrl);
        mainCtrl.setWorkspaceCtrl(workspaceCtrl);
    }

    @Test
    public void testConstructor() {
        WorkspaceCtrl test = new WorkspaceCtrl(server, mainCtrl);
        assertNotNull(test);
        assertEquals(test.getServer(), server);
        assertEquals(test.getMainCtrl(), mainCtrl);
        assertEquals(test.getBoards(), new ArrayList<BoardDisplayWorkspace>());
    }

    @Test
    public void testGetServer() {
        assertEquals(workspaceCtrl.getServer(), server);
    }

    @Test
    public void testGetMainCtrl() {
        assertEquals(workspaceCtrl.getMainCtrl(), mainCtrl);
    }

    @Test
    public void testGetterSetterBoardViewPane() {
        AnchorPane pane = new AnchorPane();
        workspaceCtrl.setBoardViewPane(pane);
        assertEquals(workspaceCtrl.getBoardViewPane(), pane);
    }

    @Test
    public void testGetterSetterBoardWorkspace() {
        VBox box = new VBox();
        workspaceCtrl.setBoardWorkspace(box);
        assertEquals(workspaceCtrl.getBoardWorkspace(), box);
    }

    @Test
    public void testAddBoard() {
        workspaceCtrl.addBoard();
        Board expected = new Board("added", "");
        expected.id = 0;
        assertTrue(server.getBoards().contains(expected));
        assertEquals(mainCtrl.getCalledMethods(), List.of("addBoard"));
    }

    @Test
    public void testJoinBoard() {
        workspaceCtrl.joinBoard();
        assertEquals(mainCtrl.getCalledMethods(), List.of("joinBoard"));
    }

    @Test
    public void testAdmin() {
        workspaceCtrl.admin();
        /* TODO after implementing admin */
    }

    @Test
    public void testSetBoardView() {
        AnchorPane pane = new AnchorPane();
        Parent parent = new AnchorPane();
        workspaceCtrl.setBoardViewPane(pane);
        workspaceCtrl.setBoardView(parent);
        Parent expected = workspaceCtrl.getBoardViewPane();
        assertEquals(workspaceCtrl.getBoardViewPane(), expected);
    }

    @Test
    public void testAddBoardToWorkspace() {
        AnchorPane pane = new AnchorPane();
        Parent parent = new AnchorPane();
        workspaceCtrl.setBoardViewPane(pane);
        workspaceCtrl.setBoardView(parent);
        workspaceCtrl.setBoardWorkspace(new VBox());
        workspaceCtrl.addBoardToWorkspace(new Board("testBoard", ""));
        assertEquals(1, workspaceCtrl.getBoardWorkspace().getChildren().size());
    }

    @Test
    public void testRemoveBoardWorkspaceFromWorkspace() {
        AnchorPane pane = new AnchorPane();
        Parent parent = new AnchorPane();
        workspaceCtrl.setBoardViewPane(pane);
        workspaceCtrl.setBoardView(parent);
        workspaceCtrl.setBoardWorkspace(new VBox());
        BoardDisplayWorkspace display = new BoardDisplayWorkspace(server, mainCtrl);
        display.setRoot(new VBox());
        workspaceCtrl.getBoardWorkspace().getChildren().add(display.getRoot());

        assertEquals(1, workspaceCtrl.getBoardWorkspace().getChildren().size());

        workspaceCtrl.removeFromWorkspace(display);

        assertEquals(0, workspaceCtrl.getBoardWorkspace().getChildren().size());
    }

    @Test
    public void testRemoveBoardFromWorkspace() throws IOException {
        VBox root = new VBox();
        Board newBoard = new Board("test", "");
        BoardCtrl ctrl = mock(BoardCtrl.class);
        BoardDisplayWorkspace display = mock(BoardDisplayWorkspace.class);
        when(display.getRoot()).thenReturn(root);
        when(display.getBoardCtrl()).thenReturn(ctrl);
        when(ctrl.getBoard()).thenReturn(newBoard);

        workspaceCtrl.getBoards().add(display);
        workspaceCtrl.setBoardWorkspace(new VBox());
        workspaceCtrl.setBoardWorkspace(root);

        //check if the display has been added
        assertEquals(workspaceCtrl.getBoards().size(), 1);

        workspaceCtrl.removeFromWorkspace(newBoard);

        //check the removal
        assertEquals(workspaceCtrl.getBoardWorkspace().getChildren().size(), 0);
        assertEquals(workspaceCtrl.getBoards().size(), 0);
    }

    @Test
    public void testUpdateBoardFromWorkspace() throws IOException {
        VBox root = new VBox();
        Board newBoard = new Board("test", "");
        Board updated = new Board("updated", "");
        BoardCtrl ctrl = mock(BoardCtrl.class);
        BoardDisplayWorkspace display = mock(BoardDisplayWorkspace.class);
        when(display.getRoot()).thenReturn(root);
        when(display.getBoardCtrl()).thenReturn(ctrl);
        when(ctrl.getBoard()).thenReturn(newBoard);
        doAnswer(invocation -> {
            Board board = invocation.getArgument(0);
            newBoard.name = board.name;
            return null;
        }).when(ctrl).setBoard(Mockito.any(Board.class));

        workspaceCtrl.getBoards().add(display);
        workspaceCtrl.setBoardWorkspace(new VBox());
        workspaceCtrl.setBoardWorkspace(root);

        //check if the display has been added
        assertEquals(workspaceCtrl.getBoards().size(), 1);

        workspaceCtrl.updateBoard(updated);

        //check that the board was updated
        assertEquals(workspaceCtrl.getBoards().size(), 1);
        assertEquals(workspaceCtrl.getBoards().get(0).getBoardCtrl().getBoard(), updated);
    }

}

