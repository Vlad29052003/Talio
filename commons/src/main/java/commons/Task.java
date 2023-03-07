package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.SIMPLE_STYLE;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    TaskList list;

    public String name;
    public long index;
    public String description;

    @ElementCollection // 1
    public List<String> subtasks;

    /**
     * Empty constructor for object mappers.
     */
    @SuppressWarnings("unused")
    public Task() {
        // for object mappers
    }

    /**
     * Constructor method.
     *
     * @param name is the name of the task.
     * @param index is the position within the TaskList.
     * @param description is the description.
     */
    public Task(String name, long index, String description) {
        this.name = name;
        this.index = index;
        this.description = description;
        subtasks = new ArrayList<>();
    }

    /**
     * Sets the taskList.
     *
     * @param taskList is the TaskList object.
     */
    public void setTaskList(TaskList taskList) {
        if(taskList == null) return;
        if(this.list != null) {
            this.list.removeTask(this);
        }
        taskList.addTask(this);
    }

    /**
     * Adds a subtask.
     *
     * @param subTask is the subtask.
     */
    public void addSubTask(String subTask){
        this.subtasks.add(subTask);
    }

    /**
     * Removes a subtask.
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
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Converts this object to a human-readable
     * String representation.
     *
     * @return the String representation.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, SIMPLE_STYLE);
    }
}