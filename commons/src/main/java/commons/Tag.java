package commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String name;
    public String color;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<Task> tasks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    public Board board;

    /**
     * Creates a new {@link Tag} object.
     */
    public Tag() {
        this.tasks = new HashSet<>();
    }

    /**
     * Creates a new {@link Tag} object.
     *
     * @param name is the name.
     * @param color is the color.
     */
    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
        this.tasks = new HashSet<>();
    }

    /**
     * Adds a Task to the list.
     *
     * @param task is the task.
     */
    public void applyTo(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a Task from the list.
     * @param task
     */
    public void removeFrom(Task task) {
        tasks.remove(task);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        ArrayList<String> exclude = new ArrayList<>();
        exclude.add("tags");
        exclude.add("lists");
        return HashCodeBuilder.reflectionHashCode(this, exclude);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
