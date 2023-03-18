package client;

import client.scenes.BoardCtrl;
import client.scenes.BoardDisplayWorkspace;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import client.scenes.crud.ConfirmTaskDeleteCtrl;
import client.scenes.crud.board.CreateNewBoardCtrl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(WorkspaceCtrl.class).in(Scopes.SINGLETON);
        binder.bind(CreateNewBoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ConfirmTaskDeleteCtrl.class).in(Scopes.SINGLETON);
        //we want a BoardCtrl for every Board added
        binder.bind(BoardCtrl.class).in(Scopes.NO_SCOPE);
        binder.bind(BoardDisplayWorkspace.class).in(Scopes.NO_SCOPE);
    }
}