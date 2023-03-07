package commons;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tag {
    //just for avoiding errors
    @Id
    public int id;
}
