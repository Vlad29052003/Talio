package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;
    public String backgroundColor;
    public String password;                 // if no password, no need to check RW permission
    public boolean RWpermission;            // true - both read and write, false - read only

    @OneToMany(cascade = CascadeType.PERSIST)
    public Set<TaskList> lists;
    @OneToMany(cascade = CascadeType.PERSIST)
    public Set<Tag> tags;

    @SuppressWarnings("unused")
    public Board() {
        // for object mappers
    }

    public Board(String name,
                 String backgroundColor,
                 String password,
                 boolean RWpermission) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.password = password;
        this.RWpermission = RWpermission;
        this.lists = new HashSet<>();
        this.tags = new HashSet<>();
    }

    public Board(String name,
                 String backgroundColor) {
        this(name, backgroundColor, "", false);
    }

    public void addTaskList(TaskList list)
    {
        if(list == null) return;
        if(list.board != null) list.board.removeTaskList(list);
        this.lists.add(list);
        list.board = this;
    }
    public void removeTaskList(TaskList list)
    {
        if(list == null) return;
        if(this.lists.remove(list)) {
            list.board = null;
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
