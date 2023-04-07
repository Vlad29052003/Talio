package scenes.crud.task.addtag;

import client.scenes.MainCtrl;
import client.scenes.TagListingCtrl;
import client.scenes.crud.task.addtag.AddTagListingCtrl;
import commons.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class TestAddTagListingCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private AddTagListingCtrl addTagListingCtrl;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.addTagListingCtrl = new AddTagListingCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        TagListingCtrl ctrl = new TagListingCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetTask() {
        Task t = new Task();
        addTagListingCtrl.setTask(t);
        assertEquals(addTagListingCtrl.getTask(), t);
    }

    @Test
    public void testGetTag() {
        assertNull(addTagListingCtrl.getTag());
    }
}
