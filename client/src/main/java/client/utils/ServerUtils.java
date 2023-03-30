package client.utils;

import commons.Board;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private String server = "http://localhost:8080/";

    /**
     * Gets the server.
     *
     * @return the server.
     */
    public String getServer() {
        return server;
    }

    /**
     * Sets the server.
     *
     * @param server is the address of another server.
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Sends a request to test the connection with the server.
     */
    public void testConnection() {
        ClientBuilder.newClient(new ClientConfig())
                .target(server).path("api/test")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get();
    }

    /**
     * Sends a request to save a Board on the server.
     *
     * @param board is the Board to be saved.
     * @return the saved Board (after server modifications).
     */
    public Board addBoard(Board board) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("api/boards")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(board, APPLICATION_JSON), Board.class);
    }

    /**
     * Sends a request to get a Board from the server.
     *
     * @param id is the id of the requested board.
     * @return the Board, if found.
     */
    public Board joinBoard(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("api/boards/" + id)
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
                .target(server).path("api/boards/" + board.id)
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
                .target(server).path("api/boards/" + board.id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(board, APPLICATION_JSON), Board.class);
    }

    /**
     * Sends a request to update the list and index of a task.
     *
     * @param newListId is the id of the new list.
     * @param index is the new index.
     * @param taskId is the id of the task.
     */
    public void dragAndDrop(long newListId, int index, long taskId) {
        String response = null;
        String path = String.valueOf(newListId) + '/' + index + '/' + taskId;
        ClientBuilder.newClient(new ClientConfig())
                .target(server).path("api/task/" + path)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(response, APPLICATION_JSON), String.class);
    }
}