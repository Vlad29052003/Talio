package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class TaskList
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;
    public long index;

    @OneToMany(cascade = CascadeType.PERSIST)
    public Set<Task> tasks;

    @SuppressWarnings("unused")
    public TaskList() {
        // for object mappers
    }

    public TaskList(long id,
                 String name,
                 long index,
                 Set<Task> tasks) {
        this.id = id;
        this.name = name;
        this.index = index;
        this.tasks = tasks;
    }

    public TaskList(long id, String name, long index) {
        this(id, name, index, new HashSet<>());
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
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
