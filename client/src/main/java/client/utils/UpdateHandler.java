package client.utils;

import commons.Board;
import commons.messages.BoardUpdateMessage;

public abstract class UpdateHandler {

    public abstract void onBoardCreated(Board board);
    public abstract void onBoardDeleted(long id);
    public abstract void onBoardUpdated(Board board);

    public void dispatchBoardUpdate(BoardUpdateMessage update){
        if(!(update.getObject() instanceof Board || update.getObject() == null)) return;
        Board board = (Board) update.getObject();
        switch(update.getOperation()){
            case CREATED:
                onBoardCreated(board);
                break;
            case DELETED:
                onBoardDeleted(update.getId());
                break;
            case UPDATED:
                onBoardUpdated(board);
                break;
        }
    }

}
