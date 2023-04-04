package scenes;

import client.utils.ServerUtils;
import commons.Board;
import commons.Tag;
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
    private List<Tag> tags;
    private List<String> calledMethods;

    /**
     * Instantiate a new {@link ServerUtilsTestingMock}.
     */
    public ServerUtilsTestingMock() {
        inc = 0;
        boards = new ArrayList<>();
        lists = new ArrayList<>();
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
        calledMethods = new ArrayList<>();
    }

    /**
     * Gets the tasks.
     *
     * @return the tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Get the list of boards known by this {@link ServerUtils} mock.
     *
     * @return the list of known boards
     */
    public List<Board> getBoards() {
        return this.boards;
    }

    /**
     * Gets the lists.
     *
     * @return the lists.
     */
    public List<TaskList> getTaskLists() {
        return lists;
    }

    /**
     * Gets the list of tags.
     *
     * @return the tags.
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Gets the list of called methods.
     *
     * @return the calledMethods.
     */
    public List<String> getCalledMethods() {
        return calledMethods;
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
        if (boards.contains(board)) {
            boards.remove(board);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Board updateBoard(Board board) {
        var foundBoard = boards.stream().filter(b -> b.id == board.id).findFirst();
        if (foundBoard.isPresent()) {
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
        if (tasks.contains(task)) {
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
        if (lists.contains(taskList)) {
            lists.remove(taskList);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Tag addTag(Tag tag, long id) {
        tag.id = inc++;
        tags.add(tag);
        return tag;
    }

    @Override
    public Response delete(Tag tag) {
        if (tags.contains(tag)) {
            tags.remove(tag);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public void dragAndDrop(long newListId, int index, long taskId) {
        calledMethods.add("dragAndDrop");
    }
}
