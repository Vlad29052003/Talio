package commons.messages;

import commons.Board;

public class BoardUpdateMessage extends UpdateMessage{

    public Board board;

    /**
     * Empty constructor for object mappers.
     */
    @SuppressWarnings("unused")
    public BoardUpdateMessage(){}

    /**
     * Create a new {@link BoardUpdateMessage BoardUpdateMessage}
     *
     * @param id of the to be updated board
     * @param object the {@link Board} with update content if necessary
     * @param operation of the update
     */
    public BoardUpdateMessage(long id, Object object, Operation operation){
        super(id,operation);
        setObject(object);
    }

    @Override
    public Object getObject() {
        return board;
    }

    @Override
    public void setObject(Object object) {
        if(!(object instanceof Board)) return;
        board = (Board) object;
    }

}
