package scenes.crud.tag;

import client.scenes.MainCtrl;
import client.scenes.crud.tag.DeleteTagCtrl;
import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scenes.ServerUtilsTestingMock;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestDeleteTagCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private DeleteTagCtrl deleteCtrl;
    private Tag tag;

    @BeforeEach
    public void setUp() {
        this.server = new ServerUtilsTestingMock();
        this.mainCtrl = mock(MainCtrl.class);
        this.deleteCtrl = new DeleteTagCtrl(server, mainCtrl);
        this.tag = new Tag("testing", "");
    }

    @Test
    public void testConstructor() {
        DeleteTagCtrl ctrl = new DeleteTagCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetSetTask() {
        deleteCtrl.setTag(tag);
        assertEquals(deleteCtrl.getTag(), tag);
    }

    @Test
    public void testCancel() {
        deleteCtrl.cancel();
        verify(mainCtrl, times(1)).hideSecondPopup();
    }

    @Test
    public void testConfirm() {
        server.addTag(tag, 1L);
        deleteCtrl.setTag(tag);
        deleteCtrl.delete();
        assertEquals(server.getTags(), new ArrayList<>());
        verify(mainCtrl, times(1)).hideSecondPopup();
    }
}

