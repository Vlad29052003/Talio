package client.utils;

import commons.Board;
import commons.TaskList;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Sends a request to save a Board on the server.
     *
     * @param board is the Board to be saved.
     * @return the saved Board (after server modifications).
     */
    public Board addBoard(Board board) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/boards")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(board, APPLICATION_JSON), Board.class);
    }

    /**
     * Sends a request to save a TaskList on the server.
     *
     * @param taskList is the TaskList to be saved.
     * @param id is the id of the board.
     * @return the saved TaskList (after server modifications).
     */
    public Board addTaskList(TaskList taskList, long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/task_lists/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(taskList, APPLICATION_JSON), Board.class);
    }

    /**
     * Sends a request to get a Board from the server.
     *
     * @param id is the id of the requested board.
     * @return the Board, if found.
     */
    public Board joinBoard(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/boards/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Board.class);
    }

    /**
     * Sends a request to delete a Board from the server.
     *
     * @param board is the board to be deleted.
     * @return the status of the request.
     */
    public Response delete(Board board) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/boards/" + board.id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * Sends a request to update a Board on the server.
     *
     * @param board is the updated Board.
     * @return the updated, saved on the server, Board.
     */
    public Board updateBoard(Board board) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/boards/" + board.id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(board, APPLICATION_JSON), Board.class);
    }
}