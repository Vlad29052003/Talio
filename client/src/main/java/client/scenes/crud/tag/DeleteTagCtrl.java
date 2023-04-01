package client.scenes.crud.tag;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Tag;
import javax.inject.Inject;

public class DeleteTagCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Tag tag;

    @Inject
    public DeleteTagCtrl (ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public void delete() {

    }

    public void cancel() {

    }
}
