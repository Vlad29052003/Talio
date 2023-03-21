//package scenes;
//
//import client.scenes.BoardCtrl;
//import client.scenes.BoardDisplayWorkspace;
//import client.scenes.MainCtrl;
//import client.scenes.WorkspaceCtrl;
//import commons.Board;
//import javafx.scene.layout.VBox;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainCtrlTesting extends MainCtrl {
//    private List<String> calledMethods;
//    private WorkspaceCtrl workspaceCtrl;
//    private ServerUtilsTesting server;
//
//    public MainCtrlTesting() {
//        calledMethods = new ArrayList<>();
//    }
//
//    public void setServer(ServerUtilsTesting serverUtils) {
//        this.server = serverUtils;
//    }
//
//    public void setWorkspaceCtrl(WorkspaceCtrl workspaceCtrl) {
//        this.workspaceCtrl = workspaceCtrl;
//    }
//
//    public List<String> getCalledMethods() {
//        return this.calledMethods;
//    }
//
//    @Override
//    public void switchBoard(BoardCtrl newBoardCtrl) {
//        calledMethods.add("switchBoard");
//        workspaceCtrl.setBoardView(newBoardCtrl.getBoardView());
//    }
//
//    @Override
//    public void cancel() {
//        calledMethods.add("cancel");
//    }
//
//    @Override
//    public void joinBoard() {
//        calledMethods.add("joinBoard");
//    }
//
//    @Override
//    public void addBoard() {
//        calledMethods.add("addBoard");
//        Board added = new Board("added", "");
//        server.addBoard(added);
//    }
//
//    @Override
//    public void editBoard(Board board) {
//        calledMethods.add("editBoard");
//    }
//
//    @Override
//    public void deleteBoard(Board board) {
//        calledMethods.add("deleteBoard");
//    }
//
//    @Override
//    public BoardDisplayWorkspace loadBoardDisplayWorkspace(Board newBoard) {
//        BoardDisplayWorkspace boardDisplay = new BoardDisplayWorkspace(server, this);
//        boardDisplay.setRoot(new VBox());
//        return boardDisplay;
//    }
//
//    @Override
//    public void addBoardToWorkspace(Board board) {
//        workspaceCtrl.addBoardToWorkspace(board);
//    }
//
//}
