package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.SIMPLE_STYLE;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    TaskList list;

    public String name;
    public long index;
    public String description;

    @ElementCollection // 1
    public List<String> subtasks;

    @SuppressWarnings("unused")
    public Task() {
        // for object mappers
    }

    public Task(String name, long index, String description) {
        this.name = name;
        this.index = index;
        this.description = description;
        subtasks = new ArrayList<>();
    }

    public void setTaskList(TaskList taskList) {
        if(taskList == null) return;
        if(this.list != null) {
            this.list.removeTask(this);
        }
        taskList.addTask(this);
    }

    /**
     * Gets the TaskList.
     *
     * @return the TaskList.
     */
    public TaskList getTaskList() {
        return this.list;
    }

    public void addSubTask(String subTask){
        this.subtasks.add(subTask);
    }
    public boolean removeSubTask(String subTask) {
        return this.subtasks.remove(subTask);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, SIMPLE_STYLE);
    }
}