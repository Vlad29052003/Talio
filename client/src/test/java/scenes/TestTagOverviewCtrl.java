package scenes;

import client.scenes.MainCtrl;
import client.scenes.TagOverviewCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestTagOverviewCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private TagOverviewCtrl tagOverviewCtrl;

    @BeforeEach
    public void setUp() {
        server = new ServerUtilsTestingMock();
        mainCtrl = mock(MainCtrl.class);
        tagOverviewCtrl = new TagOverviewCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        TagOverviewCtrl ctrl = new TagOverviewCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetBoard() {
        assertNull(tagOverviewCtrl.getBoard());
    }

    @Test
    public void testCreateTag() {
        tagOverviewCtrl.createTag();
        verify(mainCtrl, times(1)).addTag(null);
    }

    @Test
    public void testClose() {
        tagOverviewCtrl.close();
        verify(mainCtrl, times(1)).hidePopup();
    }
}
