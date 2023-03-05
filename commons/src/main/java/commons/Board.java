package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.persistence.*;
import java.util.*;
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

    public Board(long id,
                 String name,
                 String backgroundColor,
                 String password,
                 boolean RWpermission,
                 Set<TaskList> lists,
                 Set<Tag> tags) {
        this.id = id;
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.password = password;
        this.RWpermission = RWpermission;
        this.lists = lists;
        this.tags = tags;
    }

    public Board(String name,
                 String backgroundColor,
                 String password,
                 boolean RWpermission,
                 Set<TaskList> lists,
                 Set<Tag> tags) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.password = password;
        this.RWpermission = RWpermission;
        this.lists = lists;
        this.tags = tags;
    }

    public Board(String name,
                 String backgroundColor,
                 Set<TaskList> lists,
                 Set<Tag> tags) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.lists = lists;
        this.tags = tags;
        this.password = "";
        this.RWpermission = false;
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
