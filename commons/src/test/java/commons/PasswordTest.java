package commons;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordTest {

    @Test
    public void ConstructorTest(){
        Password pass = new Password();
        assertNotNull(pass);
        assertNotNull(pass.getPassword());
    }

    @Test
    public void GeneratePasswordTest(){
        Password pass = new Password();
        String firstpassword = pass.getPassword();
        pass = new Password();
        assertNotEquals(firstpassword, pass.getPassword());
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
