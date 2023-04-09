package commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
@Transactional
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    @Transient
    public boolean edit = false;
    public String name;
    public String backgroundColor;
    public String password;                 // password for edit permissions of the board.
    public String fontColor;
    public String listBackgroundColor = "";
    public String listFontColor = "";

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonManagedReference
    public Set<TaskList> lists;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    public List<Tag> tags;

    /**
     * Empty constructor for object mappers.
     */
    @SuppressWarnings("unused")
    public Board() {
        // for object mappers
        this.lists = new HashSet<>();
        this.tags = new ArrayList<>();
    }

    /**
     * Create a new {@link Board board}
     *
     * @param name            is the name of the board.
     * @param backgroundColor is the background color of the board.
     * @param fontColor       is the font color of the board.
     * @param password is the password to access the board
     */

    public Board(String name,
                 String backgroundColor,
                 String fontColor,
                 String password) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
        this.password = password;
        this.lists = new HashSet<>();
        this.tags = new ArrayList<>();
        if(password.equals("")){
            edit = true;
        }
    }

    /**
     * Create a new {@link Board board}
     *
     * @param name            is the name of the board.
     * @param backgroundColor is the background color of the board.
     * @param fontColor is the font color of the board.
     */
    public Board(String name,
                 String backgroundColor,
                 String fontColor) {
        this(name, backgroundColor, fontColor, "");
    }

    /**
     * Create a new {@link Board board}
     *
     * @param name is the name of the board.
     */
    public Board(String name) {
        this(name, "#f4f4f4", "#000000", "");
    }

    /**
     * Adds a {@link TaskList list} to the {@link Board board}.
     *
     * @param list is the list that is added to the board.
     */
    public void addTaskList(TaskList list) {
        if (list == null) return;
        if (list.board != null) list.board.removeTaskList(list);
        this.lists.add(list);
        // TODO: Assign the list a valid index / check if it's valid.
        list.board = this;
    }

    /**
     * Removes a {@link TaskList list} from the {@link Board board}.
     *
     * @param list is the list that is removed from the board.
     */
    public void removeTaskList(TaskList list) {
        if (list == null) return;
        if (this.lists.remove(list)) {
            list.board = null;
        }
        // TODO: Update indexes of other lists?
    }

    /**
     * Sorts the tags.
     */
    public void sortTags() {
        Collections.sort(tags);
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
        exclude.add("password");
        exclude.add("edit");
        return HashCodeBuilder.reflectionHashCode(this, exclude);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
