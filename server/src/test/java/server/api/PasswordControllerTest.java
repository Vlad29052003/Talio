package server.api;



import commons.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
