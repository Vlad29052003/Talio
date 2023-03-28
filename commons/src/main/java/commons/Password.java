package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Random;


public class Password {

    private static boolean admin = false;
    private static String password = "1";

    /**
     * Constructor for the class.
     */
    public Password(){
        generatePassword();
    }

    /**
     * Generates a random 10 character string and sets it as password.
     */
    public void generatePassword(){
        Random random = new Random();
        String string = new String(Integer.toString(random.nextInt(1000000000)));

        this.password = string;
        System.out.println(string);
    }

    /**
     * Checks if the introduced text is the same as the password.
     * If they match it gives administrator privileges.
     *
     * @param text introduced string
     */
    public static void checkPassword(String text){
        admin = password.equals(text);
    }

    /**
     * Getter for admin.
     *
     * @return admin
     */
    public static boolean getAdmin(){
        return admin;
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
        return password;
    }
}
