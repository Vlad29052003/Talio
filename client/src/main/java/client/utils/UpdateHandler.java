package client.utils;

import commons.Board;
import commons.messages.BoardUpdateMessage;

public abstract class UpdateHandler {

    /**
     * @param board to be added
     */
    public abstract void onBoardCreated(Board board);


    /**
     * @param id of the board to be deleted
     */
    public abstract void onBoardDeleted(long id);

    /**
     * @param board to be updated
     */
    public abstract void onBoardUpdated(Board board);

    /**
     * Dispatches an event to one of the methods
     *
     * @param update to be dispatched
     */
    public final void dispatchBoardUpdate(BoardUpdateMessage update) {
        if (!(update.getObject() instanceof Board || update.getObject() == null)) return;
        Board board = (Board) update.getObject();
        switch (update.getOperation()) {
            case CREATED:
                onBoardCreated(board);
                break;
            case DELETED:
                onBoardDeleted(update.getId());
                break;
            case UPDATED:
                onBoardUpdated(board);
                break;
            default:
                break;
        }
    }

}
