package scenes;

import client.utils.ServerUtils;
import commons.Board;
import jakarta.ws.rs.core.Response;

public class ServerUtilsTesting extends ServerUtils {
    private long inc;

    /**
     * Instantiate a new {@link ServerUtilsTesting}.
     */
    public ServerUtilsTesting() {
        inc = 0;
    }

    /**
     * Dummy function for adding a {@link Board}.
     * @param board is the {@link Board} to be saved.
     * @return the added board.
     */
    public Board addBoard(Board board) {
        board.id = inc++;
        return board;
    }

    /**
     * Dummy function for deleting a {@link Board}.
     * @param board is the {@link Board} to be deleted.
     * @return a dummy HTTP response.
     */
    public Response delete(Board board) {
        if(0L <= board.id && inc >= board.id)
            return Response.ok().build();
        else return Response.status(Response.Status.NOT_FOUND).build();
    }
}
