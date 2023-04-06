package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.SIMPLE_STYLE;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Task implements Comparable<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    @JsonBackReference
    TaskList list;

    public String name;
    public int index;
    public String description;

    @ElementCollection
    public List<String> subtasks;

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
        // we append a zero to show that the subtask has not been completed yet.
        this.subtasks.add(subTask.concat("0"));
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
     * changes the value of a subtask.
     * @param subTask the name of the subtask.
     * @param newValue the new value of the subtask.
     */
    public void setSubTask(String subTask, boolean newValue) {
        Optional<String> value = this.subtasks.stream()
                .filter(x -> x.startsWith(subTask)).findFirst();

        if (!value.isPresent()) {
            return;
        }

        String oldValue = value.get();
        String newConcat = "0";
        if (newValue) {
            newConcat = "1";
        }

        String newString = oldValue.substring(0, oldValue.length() - 1).concat(newConcat);

        int index = this.subtasks.indexOf(oldValue);
        this.subtasks.set(index, newString);
    }

    /**
     * Returns the progress of the subtasks.
     *
     * @return a value from 0 to 1 that determines how many of the subtasks have been completed.
     */
    public double calculateProgress() {
        if (subtasks.size() == 0) {
            return 1.0;
        }

        return subtasks.stream().map(x -> {
            if (x.endsWith("1")) {
                return 1.0;
            }
            return 0.0;
        }).reduce((x, y) -> x + y).orElse(0.0) / (double)subtasks.size();
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
        return ToStringBuilder.reflectionToString(this, SIMPLE_STYLE);
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