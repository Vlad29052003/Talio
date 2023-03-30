package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TagTest {

    @Test
    public void ConstructorTest(){
        Tag tag = new Tag();
        assertNotNull(tag);
    }
}
