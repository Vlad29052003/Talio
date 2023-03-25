package client.datasaving;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientData implements Serializable {
    private List<JoinedBoardList> servers;
    private int lastActiveOn;

    public ClientData() {
        this.servers = new ArrayList<>();
        this.lastActiveOn = 0;
    }

    public List<JoinedBoardList> getServers() {
        return servers;
    }

    public int getLastActiveOn() {
        return lastActiveOn;
    }

    public void setLastActiveOn(int lastActiveOn) {
        this.lastActiveOn = lastActiveOn;
    }

    public void addJoinedBoardList(JoinedBoardList joinedBoardList) {
        if(!servers.contains(joinedBoardList)) {
            servers.add(joinedBoardList);
            lastActiveOn = servers.size() - 1;
        }
    }

    public int getJoinedBoardPosition(JoinedBoardList joinedBoardList) {
        if(servers.contains(joinedBoardList)) {
            for(int i = 0; i < servers.size(); i ++)
                if(servers.get(i).equals(joinedBoardList))
                    return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "ClientData{" +
                "servers=" + servers +
                ", lastActiveOn=" + lastActiveOn +
                '}';
    }
}
