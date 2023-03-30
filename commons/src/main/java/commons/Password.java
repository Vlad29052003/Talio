package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Random;


public class Password {

    private static boolean admin = false;
    private static String password;

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

    /**
     * Getter for password.
     *
     * @return password.
     */
    public static String getPassword(){ return password; }

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
