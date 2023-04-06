package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Random;

@Entity
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;

    /**
     * Constructor for the class.
     */
    public Password(){
        generatePassword();
    }

    /**
     * Generates a random number between 0 and 1000000000.
     */
    private void generatePassword(){
        Random random = new Random();
        String string = Integer.toString(random.nextInt(1000000000));

        password = string;
        System.out.println(password);
    }

    /**
     * Getter for password.
     *
     * @return password.
     */
    public String getPassword(){ return password; }

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
        return password;
    }
}
