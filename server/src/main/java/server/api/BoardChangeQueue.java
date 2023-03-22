package server.api;

import commons.Board;
import commons.messages.BoardUpdate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BoardChangeQueue {

    private HashMap<Long, Board> boardQueue;

    public BoardChangeQueue(){
        boardQueue = new HashMap<Long, Board>();
    }

    public boolean addChanged(long id, Board b){
        if(boardQueue.containsKey(id)) return false;
        boardQueue.put(id, b);
        return true;
    }

    public List<BoardUpdate> pollUpdates(){
        List<BoardUpdate> changes = boardQueue
            .entrySet()
            .stream()
            .map(x -> new BoardUpdate(x.getKey(), x.getValue()))
            .collect(Collectors.toList());
        boardQueue.clear();
        return changes;
    }

    public int size(){
        return boardQueue.size();
    }

}
