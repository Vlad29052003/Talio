package client.datasaving;

import org.apache.commons.lang3.builder.EqualsBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JoinedBoardList implements Serializable {
    private String server;
    private List<Long> boardIDs;

    /**
     * Creates a new {@link JoinedBoardList} object.
     *
     * @param server is the server address.
     */
    public JoinedBoardList(String server) {
        this.server = server;
        boardIDs = new ArrayList<>();
    }

    /**
     * Gets the server.
     *
     * @return the server.
     */
    public String getServer() {
        return server;
    }

    /**
     * Gets the boardIDs.
     *
     * @return the boardIDs.
     */
    public List<Long> getBoardIDs() {
        return boardIDs;
    }

    /**
     * Adds a board id to the boardIDs.
     *
     * @param id is the id.
     */
    public void addBoard(long id) {
        if (!boardIDs.contains(id))
            boardIDs.add(id);
    }

    /**
     * Removed a board id from the list.
     *
     * @param id is the removed id.
     */
    public void removeBoard(long id) {
        boardIDs.remove(id);
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
        int result = getServer() != null ? getServer().hashCode() : 0;
        result = 31 * result + (getBoardIDs() != null ? getBoardIDs().hashCode() : 0);
        return result;
    }

    /**
     * Generates a string representation of this.
     *
     * @return the string representation.
     */
    @Override
    public String toString() {
        return "JoinedBoardList{" +
                "server='" + server + '\'' +
                ", boardIDs=" + boardIDs +
                '}';
    }
}
