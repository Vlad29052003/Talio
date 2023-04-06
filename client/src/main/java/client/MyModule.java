package client;

import client.scenes.BoardCtrl;
import client.scenes.BoardListingCtrl;
import client.scenes.MainCtrl;
import client.scenes.WorkspaceCtrl;
import client.scenes.crud.admin.AccessDeniedCtrl;
import client.scenes.crud.admin.EditBoardPasswordCtrl;
import client.scenes.crud.admin.GrantAdminCtrl;
import client.scenes.crud.admin.PermissionAdminCtrl;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.board.EditPermissionCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import client.scenes.crud.board.YouHavePermissionCtrl;
import client.scenes.crud.task.ConfirmTaskDeleteCtrl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {

    /**
     * Configures the scope of all controllers.
     *
     * @param binder is used to bind the scopes.
     */
    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(WorkspaceCtrl.class).in(Scopes.SINGLETON);
        binder.bind(BoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(BoardListingCtrl.class).in(Scopes.NO_SCOPE);

        binder.bind(AccessDeniedCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditBoardPasswordCtrl.class).in(Scopes.SINGLETON);
        binder.bind(GrantAdminCtrl.class).in(Scopes.SINGLETON);
        binder.bind(PermissionAdminCtrl.class).in(Scopes.SINGLETON);

        binder.bind(JoinBoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(CreateNewBoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditBoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(DeleteBoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditPermissionCtrl.class).in(Scopes.SINGLETON);
        binder.bind(YouHavePermissionCtrl.class).in(Scopes.SINGLETON);

        binder.bind(ConfirmTaskDeleteCtrl.class).in(Scopes.SINGLETON);
    }
}