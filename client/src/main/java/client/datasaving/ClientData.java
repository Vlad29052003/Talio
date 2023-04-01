package client.datasaving;

import org.apache.commons.lang3.builder.EqualsBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientData implements Serializable {
    private List<JoinedBoardList> servers;
    private int lastActiveOn;

    /**
     * Creates a new {@link ClientData} object.
     */
    public ClientData() {
        this.servers = new ArrayList<>();
        this.lastActiveOn = 0;
    }

    /**
     * Gets the servers.
     *
     * @return the servers.
     */
    public List<JoinedBoardList> getServers() {
        return servers;
    }

    /**
     * Gets lastActiveOn.
     *
     * @return lastActiveOn.
     */
    public int getLastActiveOn() {
        return lastActiveOn;
    }

    /**
     * Sets lastActiveOn.
     *
     * @param lastActiveOn the index of the server that the user
     *                     has last been on.
     */
    public void setLastActiveOn(int lastActiveOn) {
        this.lastActiveOn = lastActiveOn;
    }

    /**
     * Adds a JoinedBoardList object to the servers list.
     *
     * @param joinedBoardList is the JoinedBoardList object.
     */
    public void addJoinedBoardList(JoinedBoardList joinedBoardList) {
        if (!servers.contains(joinedBoardList)) {
            servers.add(joinedBoardList);
            lastActiveOn = servers.size() - 1;
        }
    }

    /**
     * Gets the position of a JoinedBoardList object
     * in the servers list.
     *
     * @param joinedBoardList is the searched JoinedBoardList object.
     * @return the index, if found, -1 if not found.
     */
    public int getJoinedBoardPosition(JoinedBoardList joinedBoardList) {
        if (servers.contains(joinedBoardList)) {
            for (int i = 0; i < servers.size(); i++)
                if (servers.get(i).equals(joinedBoardList))
                    return i;
        }
        return -1;
    }

    /**
     * Compares this object with another object.
     *
     * @param o is the other object.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /**
     * Calculates the hashcode of this object.
     *
     * @return the hashcode.
     */
    @Override
    public int hashCode() {
        int result = getServers() != null ? getServers().hashCode() : 0;
        result = 31 * result + getLastActiveOn();
        return result;
    }

    /**
     * Generates a string representation of this.
     *
     * @return the string representation.
     */
    @Override
    public String toString() {
        return "ClientData{" +
                "servers=" + servers +
                ", lastActiveOn=" + lastActiveOn +
                '}';
    }
}
