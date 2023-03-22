package commons.messages;

import commons.Board;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class BoardUpdate {

    private long id;
    private Board board;

    public BoardUpdate(){}

    public BoardUpdate(long id, Board board){
        this.id = id;
        this.board = board;
    }

    public Board getBoard(){ return this.board; }

    public long getId(){ return this.id; }



    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

}
