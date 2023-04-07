package scenes;

import client.scenes.MainCtrl;
import client.scenes.TagListingCtrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestTagListingCtrl {
    private ServerUtilsTestingMock server;
    private MainCtrl mainCtrl;
    private TagListingCtrl tagListingCtrl;

    @BeforeEach
    public void setUp() {
        server = new ServerUtilsTestingMock();
        mainCtrl = mock(MainCtrl.class);
        tagListingCtrl = new TagListingCtrl(server, mainCtrl);
    }

    @Test
    public void testConstructor() {
        TagListingCtrl ctrl = new TagListingCtrl(server, mainCtrl);
        assertNotNull(ctrl);
    }

    @Test
    public void testGetTag() {
        assertNull(tagListingCtrl.getTag());
    }

    @Test
    public void testEditTag() {
        tagListingCtrl.editTag();
        verify(mainCtrl, times(1)).editTag(null);
    }

    @Test
    public void testDeleteTag() {
        tagListingCtrl.deleteTag();
        verify(mainCtrl, times(1)).deleteTag(null);
    }
}
