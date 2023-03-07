package commons;

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
    Board board;

    @OneToMany(cascade = CascadeType.PERSIST)
    public Set<Task> tasks;

    @SuppressWarnings("unused")
    public TaskList() {
        // for object mappers
    }

    public TaskList(String name,
                    long index) {
        this.name = name;
        this.index = index;
        this.tasks = new HashSet<>();
    }

    public void setBoard(Board b) {
        if(b == null) return;
        if(this.board != null) {
            this.board.removeTaskList(this);
        }
        b.addTaskList(this);
    }

    public void addTask(Task t) {
        if(t == null) return;
        if(t.list != null) t.list.removeTask(t);
        this.tasks.add(t);
        t.list = this;
    }
    public void removeTask(Task t) {
        if(t == null) return;
        if(this.tasks.remove(t)) {
            t.list = null;
        }
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
