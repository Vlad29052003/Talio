package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.ElementCollection;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Transactional
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task implements Comparable<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    TaskList list;

    public String name;
    public int index;
    public String description;

    @ElementCollection
    public List<String> subtasks;

    @Transient
    public boolean focused = false;

    /**
     * Empty constructor for object mappers.
     */
    @SuppressWarnings("unused")
    public Task() {
        // for object mappers
        subtasks = new ArrayList<>();
    }

    /**
     * Creates a new {@link Task task}.
     *
     * @param name is the name of the task.
     * @param index is the position within the TaskList.
     * @param description is the description.
     */
    public Task(String name, int index, String description) {
        this.name = name;
        this.index = index;
        this.description = description;
        subtasks = new ArrayList<>();
    }

    /**
     * Sets the parent {@link TaskList list}.
     *
     * @param taskList is the parent list of this task.
     */
    public void setTaskList(TaskList taskList) {
        if (taskList == null) return;
        if (this.list != null) {
            this.list.removeTask(this);
        }
        taskList.addTask(this);
    }

    /**
     * Gets the TaskList.
     *
     * @return the TaskList.
     */
    @JsonIgnore
    public TaskList getTaskList() {
        return this.list;
    }

    /**
     * Adds a subtask to the {@link Task#subtasks task}.
     *
     * @param subTask is the subtask.
     */
    public void addSubTask(String subTask){
        this.subtasks.add(subTask);
    }

    /**
     * Removes a subtask from the {@link Task#subtasks task}.
     *
     * @param subTask is the subtask to be removed.
     * @return true if the list contained that subtask,
     * false otherwise.
     */
    public boolean removeSubTask(String subTask) {
        return this.subtasks.remove(subTask);
    }

    /**
     * Compares object this with the given argument.
     *
     * @param obj is the object to compare with.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Calculates the hashcode of this object.
     *
     * @return the hashcode.
     */
    @Override
    public int hashCode() {
        ArrayList<String> exclude = new ArrayList<>();
        exclude.add("list");
        return HashCodeBuilder.reflectionHashCode(this, exclude);
    }

    /**
     * Converts this object to a human-readable
     * String representation.
     *
     * @return the String representation.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

    /**
     * Used to compare 2 Task objects.
     *
     * @param o the object to be compared.
     * @return 1 if this is greater, 0 if equal, -1 if o is greater.
     */
    @Override
    public int compareTo(Task o) {
        if(o == null)
            throw new NullPointerException();
        return Long.compare(this.index, o.index);
    }
}