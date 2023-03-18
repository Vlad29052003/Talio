package commons;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;
    public long index;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    Board board;

    @OneToMany(mappedBy = "list", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    public Set<Task> tasks;

    /**
     * Empty constructor for object mappers.
     */
    @SuppressWarnings("unused")
    public TaskList() {
        // for object mappers
    }

    /**
     * Create a new {@link TaskList list}.
     *
     * @param name is the name of the list.
     * @param index is the position within the board.
     */
    public TaskList(String name,
                    long index) {
        this.name = name;
        this.index = index;
        this.tasks = new HashSet<>();
    }

    /**
     * Sets the parent {@link Board board}.
     *
     * @param board is the parent board of this list.
     */
    public void setBoard(Board board) {
        if(board == null) return;
        if(this.board != null) {
            this.board.removeTaskList(this);
        }
        board.addTaskList(this);
    }

    public Board getBoard() {
        return this.board;
    }

    /**
     * Adds a {@link Task task} to the {@link TaskList list}.
     *
     * @param task is the task that is added to the list.
     */
    public void addTask(Task task) {
        if(task == null) return;
        if(task.list != null) task.list.removeTask(task);
        this.tasks.add(task);
        task.list = this;
    }

    /**
     * Removes a {@link Task task} from the {@link TaskList list}.
     *
     * @param task is the removed task.
     */
    public void removeTask(Task task) {
        if(task == null) return;
        if(this.tasks.remove(task)) {
            task.list = null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        ArrayList<String> exclude = new ArrayList<>();
        exclude.add("tasks");
        exclude.add("board");
        return HashCodeBuilder.reflectionHashCode(this, exclude);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
