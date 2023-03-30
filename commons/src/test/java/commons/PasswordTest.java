package commons;

import org.junit.jupiter.api.Test;

import static commons.Password.getAdmin;
import static commons.Password.getPassword;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordTest {

    @Test
    public void ConstructorTest(){
        Password pass = new Password();
        assertNotNull(pass);
        assertNotNull(getPassword());
    }

    @Test
    public void GeneratePasswordTest(){
        Password pass = new Password();
        String firstpassword = getPassword();
        pass = new Password();
        assertNotEquals(firstpassword, getPassword());
    }

    @Test
    public void CheckPassowrdTest(){
        Password pass = new Password();
        assertFalse(getAdmin());

        Password.checkPassword(getPassword());
        assertTrue(getAdmin());

        Password.checkPassword("123");
        assertFalse(getAdmin());
    }

    @Test
    public void EqualsTest(){
        Password pass = new Password();

        assertTrue(pass.equals(pass));
    }

    @Test
    public void HashcodeTest(){
        Password pass = new Password();

        assertNotNull(pass.hashCode());
    }

    @Test
    public void ToStringTest(){
        Password pass = new Password();

        assertNotNull(pass.toString());
    }

}
