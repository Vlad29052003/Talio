package client;

import client.scenes.BoardCtrl;
import client.scenes.BoardListingCtrl;
import client.scenes.MainCtrl;
import client.scenes.TagListingCtrl;
import client.scenes.TagOverviewCtrl;
import client.scenes.TaskCtrl;
import client.scenes.TaskListCtrl;
import client.scenes.WorkspaceCtrl;
import client.scenes.crud.admin.AccessDeniedCtrl;
import client.scenes.crud.admin.EditBoardPasswordCtrl;
import client.scenes.crud.admin.GrantAdminCtrl;
import client.scenes.crud.admin.PermissionAdminCtrl;
import client.scenes.crud.board.CreateNewBoardCtrl;
import client.scenes.crud.board.DeleteBoardCtrl;
import client.scenes.crud.board.EditBoardCtrl;
import client.scenes.crud.board.UnlockBoardCtrl;
import client.scenes.crud.board.JoinBoardCtrl;
import client.scenes.crud.board.YouHavePermissionCtrl;
import client.scenes.crud.tag.CreateTagCtrl;
import client.scenes.crud.tag.DeleteTagCtrl;
import client.scenes.crud.tag.EditTagCtrl;
import client.scenes.crud.task.CreateTaskCtrl;
import client.scenes.crud.task.DeleteTaskCtrl;
import client.scenes.crud.task.EditTaskCtrl;
import client.scenes.crud.task.addtag.AddTagListingCtrl;
import client.scenes.crud.tasklists.CreateTaskListCtrl;
import client.scenes.crud.tasklists.DeleteTaskListCtrl;
import client.scenes.crud.tasklists.EditTaskListCtrl;
import client.utils.ServerUtils;
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
        binder.bind(ServerUtils.class).in(Scopes.SINGLETON);
        binder.bind(WorkspaceCtrl.class).in(Scopes.SINGLETON);
        binder.bind(BoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(BoardListingCtrl.class).in(Scopes.NO_SCOPE);
        binder.bind(TagOverviewCtrl.class).in(Scopes.SINGLETON);

        binder.bind(AccessDeniedCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditBoardPasswordCtrl.class).in(Scopes.SINGLETON);
        binder.bind(GrantAdminCtrl.class).in(Scopes.SINGLETON);
        binder.bind(PermissionAdminCtrl.class).in(Scopes.SINGLETON);

        binder.bind(JoinBoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(CreateNewBoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditBoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(DeleteBoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(UnlockBoardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(YouHavePermissionCtrl.class).in(Scopes.SINGLETON);

        binder.bind(CreateTaskListCtrl.class).in(Scopes.SINGLETON);
        binder.bind(DeleteTaskListCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditTaskListCtrl.class).in(Scopes.SINGLETON);

        binder.bind(DeleteTaskCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditTaskCtrl.class).in(Scopes.SINGLETON);
        binder.bind(CreateTaskCtrl.class).in(Scopes.SINGLETON);

        binder.bind(DeleteTagCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditTagCtrl.class).in(Scopes.SINGLETON);
        binder.bind(CreateTagCtrl.class).in(Scopes.SINGLETON);

        binder.bind(TaskListCtrl.class).in(Scopes.NO_SCOPE);
        binder.bind(TaskCtrl.class).in(Scopes.NO_SCOPE);
        binder.bind(BoardListingCtrl.class).in(Scopes.NO_SCOPE);
        binder.bind(TagListingCtrl.class).in(Scopes.NO_SCOPE);
        binder.bind(AddTagListingCtrl.class).in(Scopes.NO_SCOPE);
    }
}