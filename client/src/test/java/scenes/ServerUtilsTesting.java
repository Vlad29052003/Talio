package scenes;

import client.utils.ServerUtils;
import commons.Board;
import jakarta.ws.rs.core.Response;

public class ServerUtilsTesting extends ServerUtils {
    private long inc;
    public ServerUtilsTesting() {
        inc = 0;
    }

    public Board addBoard(Board board) {
        board.id = inc++;
        return board;
    }

    public Response delete(Board board) {
        if(0L <= board.id && inc >= board.id)
            return Response.ok().build();
        else return Response.status(Response.Status.NOT_FOUND).build();
    }
}
