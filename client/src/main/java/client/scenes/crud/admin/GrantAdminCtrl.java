package client.scenes.crud.admin;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GrantAdminCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    @FXML
    private TextField text;

    /**
     * Creates a new {@link GrantAdminCtrl} object.
     *
     * @param server   is the ServerUtils.
     * @param mainCtrl is the MainCtrl.
     */
    @Inject
    public GrantAdminCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }


    /**
     * Bond to the Cancel button.
     * Switches back to the workspace Scene.
     */
    public void cancel() {
        reset();
        mainCtrl.cancel();
    }

    /**
     * Bound to the Confirm button.
     * Sends a request to the server
     * to test the password.
     */
    public void confirm(){
        if(text.getText().equals(server.getPassword())){
            mainCtrl.setAdminTrue();
            String boardlist = server.addAllBoards();
            List<String> boards = Arrays.asList(boardlist.split("\"id\":"));
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
            mainCtrl.permissionAdmin();
        }else{
            reset();
            mainCtrl.accessDenied();
        }
    }

    /**
     * Resets the fields in this object.
     */
    private void reset() {
        text.setText("");
    }

}
