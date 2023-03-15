package client;

import client.scenes.BoardCtrl;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(WorkspaceCtrl.class).in(Scopes.SINGLETON);

        //we want a BoardCtrl for every Board added
        binder.bind(BoardCtrl.class).in(Scopes.NO_SCOPE);
    }
}