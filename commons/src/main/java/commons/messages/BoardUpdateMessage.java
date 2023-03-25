package commons.messages;

import commons.Board;

public class BoardUpdateMessage extends UpdateMessage{

    public Board board;

    // For object mappers
    @SuppressWarnings("unused")
    public BoardUpdateMessage(){}

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
