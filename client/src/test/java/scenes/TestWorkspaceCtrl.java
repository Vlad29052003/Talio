package scenes;

import client.scenes.BoardListingCtrl;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import commons.Board;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestWorkspaceCtrl {
    private WorkspaceCtrl workspaceCtrl;
    private MainCtrl mainCtrl;
    private ServerUtilsTestingMock server;

    @BeforeEach
    public void setUp() {
        this.mainCtrl = mock(MainCtrl.class);
        this.server = new ServerUtilsTestingMock();
        this.workspaceCtrl = new WorkspaceCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        WorkspaceCtrl test = new WorkspaceCtrl(server, mainCtrl);

        assertNotNull(test);
    }

    @Test
    public void testGetBoards() {
        assertEquals(workspaceCtrl.getBoards(), new ArrayList<BoardListingCtrl>());
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
        verify(mainCtrl, times(1)).addBoard();
    }

    @Test
    public void testJoinBoard() {
        workspaceCtrl.joinBoard();
        verify(mainCtrl, times(1)).joinBoard();
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
        Board board = new Board("testBoard", "");
        Parent displayRoot = new VBox();
        BoardListingCtrl display = mock(BoardListingCtrl.class);
        when(display.getRoot()).thenReturn(displayRoot);
        when(mainCtrl.newBoardListingView(board)).thenReturn(new Pair<>(display, displayRoot));

        AnchorPane pane = new AnchorPane();
        Parent parent = new AnchorPane();
        workspaceCtrl.setBoardViewPane(pane);
        workspaceCtrl.setBoardView(parent);
        workspaceCtrl.setBoardWorkspace(new VBox());
        workspaceCtrl.addBoardToWorkspace(board);

        assertEquals(1, workspaceCtrl.getBoardWorkspace().getChildren().size());
        assertTrue(workspaceCtrl.getBoardWorkspace().getChildren().contains(displayRoot));
        verify(mainCtrl, times(1)).newBoardListingView(board);
    }

    @Test
    public void testRemoveBoardWorkspaceFromWorkspace() {
        Board board = new Board("testBoard", "");
        Parent displayRoot = new VBox();
        BoardListingCtrl display = mock(BoardListingCtrl.class);
        when(display.getRoot()).thenReturn(displayRoot);
        when(mainCtrl.newBoardListingView(board)).thenReturn(new Pair<>(display, displayRoot));

        AnchorPane pane = new AnchorPane();
        Parent parent = new AnchorPane();
        workspaceCtrl.setBoardViewPane(pane);
        workspaceCtrl.setBoardView(parent);
        workspaceCtrl.setBoardWorkspace(new VBox());
        workspaceCtrl.addBoardToWorkspace(board);

        workspaceCtrl.removeFromWorkspace(display);
        assertEquals(0, workspaceCtrl.getBoardWorkspace().getChildren().size());
    }

    @Test
    public void testRemoveBoardFromWorkspace() {
        Board board = new Board("testBoard", "");
        Parent displayRoot = new VBox();
        BoardListingCtrl display = mock(BoardListingCtrl.class);
        when(display.getRoot()).thenReturn(displayRoot);
        when(display.getBoard()).thenReturn(board);
        when(mainCtrl.newBoardListingView(board)).thenReturn(new Pair<>(display, displayRoot));

        AnchorPane pane = new AnchorPane();
        Parent parent = new AnchorPane();
        workspaceCtrl.setBoardViewPane(pane);
        workspaceCtrl.setBoardView(parent);
        workspaceCtrl.setBoardWorkspace(new VBox());
        workspaceCtrl.addBoardToWorkspace(board);

        workspaceCtrl.removeFromWorkspace(board);
        assertEquals(0, workspaceCtrl.getBoardWorkspace().getChildren().size());
    }

    @Test
    public void testUpdateBoardFromWorkspace() {
        Board board = new Board("test", "");
        Board updated = new Board("updated", "");
        Parent displayRoot = new VBox();
        BoardListingCtrl display = mock(BoardListingCtrl.class);
        when(display.getRoot()).thenReturn(displayRoot);
        when(display.getBoard()).thenReturn(board);
        when(mainCtrl.newBoardListingView(board)).thenReturn(new Pair<>(display, displayRoot));
        doAnswer(invocation -> {
            Board b = invocation.getArgument(0);
            board.name = b.name;
            return null;
        }).when(display).setBoard(Mockito.any(Board.class));

        AnchorPane pane = new AnchorPane();
        Parent parent = new AnchorPane();
        workspaceCtrl.setBoardViewPane(pane);
        workspaceCtrl.setBoardView(parent);
        workspaceCtrl.setBoardWorkspace(new VBox());
        workspaceCtrl.addBoardToWorkspace(board);

        assertEquals(workspaceCtrl.getBoards().get(0).getBoard(), board);

        workspaceCtrl.updateBoard(updated);
        assertEquals(workspaceCtrl.getBoards().get(0).getBoard(), updated);
        assertEquals(workspaceCtrl.getBoards().size(), 1);
    }

}
