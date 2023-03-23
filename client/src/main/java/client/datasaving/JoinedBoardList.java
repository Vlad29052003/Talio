package client.datasaving;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JoinedBoardList implements Serializable {
    private String server;
    private List<Long> boardIDs;

    public JoinedBoardList(String server) {
        this.server = server;
        boardIDs = new ArrayList<>();
    }

    public String getServer() {
        return server;
    }

    public List<Long> getBoardIDs() {
        return new ArrayList<Long>(boardIDs);
    }

    public void addBoard(long id) {
        if(!boardIDs.contains(id))
            boardIDs.add(id);
    }

    public void removeBoard(long id) {
        boardIDs.remove(id);
    }

    @Override
    public String toString() {
        return "JoinedBoardList{" +
                "server='" + server + '\'' +
                ", boardIDs=" + boardIDs +
                '}';
    }
}
