package scenes;

import client.utils.ServerUtils;
import commons.Board;
import commons.Task;
import commons.TaskList;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class ServerUtilsTestingMock extends ServerUtils {
    private long inc;
    private List<Board> boards;
    private List<TaskList> lists;
    private List<Task> tasks;

    /**
     * Instantiate a new {@link ServerUtilsTestingMock}.
     */
    public ServerUtilsTestingMock() {
        inc = 0;
        boards = new ArrayList<>();
        lists = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public List<TaskList> getLists() {
        return lists;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Get the list of boards known by this {@link ServerUtils} mock.
     * @return the list of known boards
     */
    public List<Board> getBoards() {
        return this.boards;
    }

    @Override
    public Board addBoard(Board board) {
        board.id = inc++;
        boards.add(board);
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

    @Override
    public Task addTask(Task task, long id) {
        task.id = inc++;
        tasks.add(task);
        return task;
    }

    @Override
    public Response delete(Task task) {
        if(tasks.contains(task)){
            tasks.remove(task);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Board addTaskList(TaskList taskList, long id) {
        taskList.id = inc++;
        lists.add(taskList);
        return new Board();
    }

    @Override
    public Response deleteTaskList(TaskList taskList) {
        if(tasks.contains(taskList)){
            tasks.remove(taskList);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
