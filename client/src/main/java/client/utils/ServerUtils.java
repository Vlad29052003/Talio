package client.utils;

import commons.Board;
import commons.Tag;
import commons.Task;
import commons.TaskList;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {
    private final ExecutorService TASK_EXEC = Executors.newSingleThreadExecutor();
    private final ExecutorService TAG_EXEC = Executors.newSingleThreadExecutor();
    private ServerURL url = new ServerURL("localhost", 8080);

    /**
     * Gets the server.
     *
     * @return the server.
     */
    public String getServerAddress() {
        return "http://" + url.toString();
    }

    /**
     * @return the {@link ServerURL} object.
     */
    public ServerURL getServer(){
        return this.url;
    }

    /**
     * Sets the server.
     *
     * @param url is the address of another server.
     */
    public void setServer(ServerURL url) {
        this.url = url;
    }

    /**
     * Sends a request to test the connection with the server.
     * @return Boolean whether the set server is reachable
     */
    public boolean testConnection() {
        try{
            return ClientBuilder.newClient(new ClientConfig())
                    .target(getServerAddress()).path("api/test")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get().getStatus() == 200;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Sends a request to save a Board on the server.
     *
     * @param board is the Board to be saved.
     * @return the saved Board (after server modifications).
     */
    public Board addBoard(Board board) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/boards")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(board, APPLICATION_JSON), Board.class);
    }

    /**
     * Sends a request to save a TaskList on the server.
     *
     * @param taskList is the TaskList to be saved.
     * @param id       is the id of the board.
     * @return the saved TaskList (after server modifications).
     */
    public Board addTaskList(TaskList taskList, long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/task_lists/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(taskList, APPLICATION_JSON), Board.class);
    }

    /**
     * Saves a task on the server.
     *
     * @param task is the Task to be saved.
     * @param id   is the id of the TaskList.
     * @return the saved Task.
     */
    public Task addTask(Task task, long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/task/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(task, APPLICATION_JSON), Task.class);
    }

    /**
     * Saves a tag on the server.
     *
     * @param tag     is the Tag to be saved.
     * @param boardId is the id of the Board.
     * @return the saved Tag.
     */
    public Tag addTag(Tag tag, long boardId) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/tag/" + boardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(tag, APPLICATION_JSON), Tag.class);
    }

    /**
     * Sends a request to get a Board from the server.
     *
     * @param id is the id of the requested board.
     * @return the Board, if found.
     */
    public Board joinBoard(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/boards/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Board.class);
    }

    /**
     * Sends a request to get all Boards from the server.
     *
     * @return a string with all boards.
     */
    public List<Board> addAllBoards() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/boards")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Board>>() {});
    }

    /**
     * Sends a request to delete a Board from the server.
     *
     * @param board is the Board to be deleted.
     * @return the status of the request.
     */
    public Response delete(Board board) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/boards/" + board.id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * Sends a request to delete a TaskList from the server.
     *
     * @param taskList is the TaskList to be deleted.
     * @return the status of the request.
     */
    public Response deleteTaskList(TaskList taskList) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/task_lists/" + taskList.id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * Sends a request to delete a Task from the server.
     *
     * @param task is the Task to be deleted.
     * @return the status of the request.
     */
    public Response delete(Task task) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/task/" + task.id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * Sends a request to delete a Tag from the server.
     *
     * @param tag is the Tag to be deleted.
     * @return the status of the request.
     */
    public Response delete(Tag tag) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/tag/" + tag.id)
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
                .target(getServerAddress()).path("api/boards/" + board.id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(board, APPLICATION_JSON), Board.class);
    }

    /**
     * Sends a request to get the password from the server.
     *
     * @return the password.
     */
    public String getPassword() {
        String pass = ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/password")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(String.class);
        pass = pass.substring(14).split("\"")[0];
        return pass;
    }

    /**
     * Sends a request to update a TaskList on the server.
     *
     * @param taskList is the updated taskList.
     * @return the updated, saved on the server, TaskList.
     */
    public TaskList editTaskList(TaskList taskList) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/task_lists/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(taskList, APPLICATION_JSON), TaskList.class);
    }

    /**
     * Sends a request to update a Task on the server.
     *
     * @param task is the updated Task.
     */
    public void updateTask(Task task) {
        ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/task/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(task, APPLICATION_JSON), String.class);
    }

    /**
     * Sends a request to update a Tag on the server.
     *
     * @param tag is the updated Tag.
     * @return the updated Tag.
     */
    public Tag updateTag(Tag tag) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/tag/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(tag, APPLICATION_JSON), Tag.class);
    }

    /**
     * Sends a request to update the list and index of a task.
     *
     * @param newListId is the id of the new list.
     * @param index     is the new index.
     * @param taskId    is the id of the task.
     */
    public void dragAndDrop(long newListId, int index, long taskId) {
        String response = null;
        String path = String.valueOf(newListId) + '/' + index + '/' + taskId;
        ClientBuilder.newClient(new ClientConfig())
                .target(getServerAddress()).path("api/task/" + path)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(response, APPLICATION_JSON), String.class);
    }

    /**
     * Starts the long polling for Task updates.
     *
     * @param consumer is the Consumer.
     */
    public void registerForTaskUpdates(Consumer<Board> consumer) {
        TASK_EXEC.submit(() -> {
            while (!Thread.interrupted()) {
                try {
                    var res = ClientBuilder.newClient(new ClientConfig())
                            .target(getServerAddress()).path("api/task/getUpdates")
                            .request(APPLICATION_JSON)
                            .accept(APPLICATION_JSON)
                            .get(Response.class);
                    if (res.getStatus() == 204) {
                        continue;
                    }
                    Board b = res.readEntity(Board.class);
                    consumer.accept(b);
                } catch (Exception ignored) {
                }
            }
        });
    }

    /**
     * Starts the long polling for Tag updates.
     *
     * @param consumer is the Consumer.
     */
    public void registerForTagUpdates(Consumer<Board> consumer) {
        TAG_EXEC.submit(() -> {
            while (!Thread.interrupted()) {
                try {
                    var res = ClientBuilder.newClient(new ClientConfig())
                            .target(getServerAddress()).path("api/tag/getUpdates")
                            .request(APPLICATION_JSON)
                            .accept(APPLICATION_JSON)
                            .get(Response.class);
                    if (res.getStatus() == 204) {
                        continue;
                    }
                    Board b = res.readEntity(Board.class);
                    consumer.accept(b);
                } catch (Exception ignored) {
                }
            }
        });
    }

    /**
     * Ensures the threads stop when the application is closed.
     */
    public void stop() {
        TASK_EXEC.shutdownNow();
        TAG_EXEC.shutdownNow();
    }
}
