package scenes;

import client.utils.ServerUtils;
import commons.Board;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class ServerUtilsTesting extends ServerUtils {
    private long inc;
    private List<Board> boards;

    public ServerUtilsTesting() {
        inc = 0;
        boards = new ArrayList<>();
    }

    public List<Board> getBoards() {
        return this.boards;
    }

    @Override
    public Board addBoard(Board board) {
        board.id = inc++;
<<<<<<< HEAD
        boards.add(board);
=======
>>>>>>> main
        return board;
    }

    @Override
    public Board joinBoard(long id) {
        var board = boards.stream().filter(b -> b.id == id).findFirst();
        return board.orElse(null);
    }

    @Override
    public Response delete(Board board) {
        if(boards.contains(board)) {
            boards.remove(board);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Board updateBoard(Board board) {
        var foundBoard = boards.stream().filter(b -> b.id == board.id).findFirst();
        if(foundBoard.isPresent()) {
            Board toBeUpdated = foundBoard.get();
            toBeUpdated.name = board.name;
            return toBeUpdated;
        }
        return null;
    }
}
