package client.scenes.crud.board;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class YouHavePermissionCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;

    /**
     * Creates a new {@link YouHavePermissionCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public YouHavePermissionCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Bond to the Ok button.
     * Switches back to the workspace Scene.
     */
    public void ok() {
        String text = server.addAllBoards();
        List<String> boards = Arrays.asList(text.split("\"id\":"));
        boards = boards.subList(1,boards.size());
        List<Long> ids = boards.stream()
                .map(x -> x.charAt(0))
                .map(x -> Character.getNumericValue(x))
                .map(x -> (long)x)
                .collect(Collectors.toList());
        List<Board> list = new ArrayList<>();
        for(int i = 0; i < ids.size();i++){
            list.add(server.joinBoard(ids.get(i)));
        }
        mainCtrl.addBoardListToWorkspace(list);
        mainCtrl.cancel();
    }
}
