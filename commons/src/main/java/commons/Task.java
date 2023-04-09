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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.ElementCollection;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Transactional
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task implements Comparable<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    @JsonBackReference
    TaskList list;

    @ManyToMany(mappedBy = "tasks")
    public Set<Tag> tags;

    public String name;
    public int index;
    public String description;
    public String color;

    @ElementCollection
    public List<String> subtasks;

    @Transient
    public boolean focused = false;

    /**
     * Empty constructor for object mappers.
     */
    @SuppressWarnings("unused")
    public Task() {
        this.subtasks = new ArrayList<>();
        this.tags = new HashSet<>();
    }

    /**
     * Creates a new {@link Task task}.
     *
     * @param name        is the name of the task.
     * @param index       is the position within the TaskList.
     * @param description is the description.
     * @param color is the color of the task.
     */
    public Task(String name, int index, String description, String color) {
        this.name = name;
        this.index = index;
        this.description = description;
        this.subtasks = new ArrayList<>();
        this.color = color;
        this.tags = new HashSet<>();
    }

    /**
     * Creates a new {@link Task task}.
     *
     * @param name is the name of the task.
     * @param index is the position within the TaskList.
     * @param description is the description.
     */
    public Task(String name, int index, String description) {
        this(name, index, description, "#f4f4f4");
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
     * @return whether the subtask is successfully added.
     */
    public boolean addSubTask(String subTask) {
        // There already exists a subtask with this name
        if (this.subtasks.stream().anyMatch(x -> x.startsWith(subTask) &&
                x.length() == subTask.length() + 1)) {
            return false;
        }

        // we append a zero to show that the subtask has not been completed yet.
        this.subtasks.add(subTask.concat("0"));

        return true;
    }

    /**
     * Removes a subtask from the {@link Task#subtasks task}.
     *
     * @param subTask is the subtask to be removed.
     * @return true if the list contained that subtask,
     * false otherwise.
     */
    public boolean removeSubTask(String subTask) {
        return this.subtasks
                .removeIf(x -> x.startsWith(subTask) &&
                        x.length() == subTask.length() + 1);
    }

    /**
     * changes the value of a subtask.
     * @param subTask the name of the subtask.
     * @param newValue the new value of the subtask.
     */
    public void setSubTask(String subTask, boolean newValue) {
        String booleanSearched = "1";
        if(newValue) booleanSearched = "0";
        String searched = subTask + booleanSearched;

        String newConcat = "0";
        if (newValue) {
            newConcat = "1";
        }

        int index = this.subtasks.indexOf(searched);
        if(index == -1)return;

        this.subtasks.set(index, subTask + newConcat);
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
        }).reduce(Double::sum).orElse(0.0) / (double)subtasks.size();
    }

    /**
     * Gets the number of completed subtasks.
     *
     * @return the number of completed subtasks.
     */
    public int completedSubtasks() {
        return (int) subtasks.stream().filter(st -> st.endsWith("1")).count();
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
        exclude.add("tags");
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
        if (o == null)
            throw new NullPointerException();
        return Long.compare(this.index, o.index);
    }
}