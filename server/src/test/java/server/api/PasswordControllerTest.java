package server.api;



import commons.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordControllerTest {

    private TestPasswordRepository repo;
    private PasswordController sut;

    @BeforeEach
    public void setup() {
        repo = new TestPasswordRepository();
        sut = new PasswordController(repo);
    }

    @Test
    public void newRepository(){
        List<Password> allPasswords = sut.getAll();
        assertEquals(0, allPasswords.size());
    }

    @Test
    public void createNewPassword(){
        var actual = sut.createNewPassword();
        if(actual.getBody() == null) return;
        var actual2 = sut.createNewPassword();
        if(actual2.getBody() == null) return;

        List<Password> allPasswords = sut.getAll();
        assertEquals(1, allPasswords.size());
        assertTrue(allPasswords.contains(actual2.getBody()));
    }
}
