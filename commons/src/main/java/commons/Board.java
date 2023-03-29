package commons;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonManagedReference
    public Set<TaskList> lists;
    @OneToMany(cascade = CascadeType.ALL)
    public Set<Tag> tags;

    /**
     * Empty constructor for object mappers.
     */
    @SuppressWarnings("unused")
    public Board() {
        // for object mappers
        lists = new HashSet<>();
    }

    /**
     * Create a new {@link Board board}
     *
     * @param name is the name of the board.
     * @param backgroundColor is the background color of the board.
     * @param password is the password to access the board
     * @param RWpermission is specifies the read and write permissions.
     */
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

    /**
     * Create a new {@link Board board}
     *
     * @param name is the name of the board.
     * @param backgroundColor is the background color of the board.
    */
    public Board(String name,
                 String backgroundColor) {
        this(name, backgroundColor, "", false);
    }

    /**
     * Adds a {@link TaskList list} to the {@link Board board}.
     *
     * @param list is the list that is added to the board.
     */
    public void addTaskList(TaskList list)
    {
        if(list == null) return;
        if(list.board != null) list.board.removeTaskList(list);
        this.lists.add(list);
        list.board = this;
    }

    /**
     * Removes a {@link TaskList list} from the {@link Board board}.
     *
     * @param list is the list that is removed from the board.
     */
    public void removeTaskList(TaskList list)
    {
        if(list == null) return;
        if(this.lists.remove(list)) {
            list.board = null;
        }
    }

    /**
     * Gets the Set of TaskLists.
     *
     * @return the Set to be returned.
     */
    public Set<TaskList> getTaskLists() {
        return lists;
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
