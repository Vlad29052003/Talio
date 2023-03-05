package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.SIMPLE_STYLE;
import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    @JoinColumn(name = "LIST_ID")
    private TaskList list;

    public String name;
    public long index;
    public String description;

    @ElementCollection // 1
    @CollectionTable(name = "SUBTASKS", joinColumns = @JoinColumn(name = "TASK_ID")) // 2
    @Column(name = "SUBTASKS") // 3
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

    public void addSubTask(String subTask){
        subtasks.add(subTask);
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