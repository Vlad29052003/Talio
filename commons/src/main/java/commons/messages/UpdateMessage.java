package commons.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class UpdateMessage {

    private long id;
    private Operation operation;

    // For object mappers
    public UpdateMessage(){}

    public UpdateMessage(long id, Operation operation){
        this.id = id;
        this.operation = operation;
    }

    @JsonIgnore
    public abstract Object getObject();
    public abstract void setObject(Object object);

    public long getId(){ return this.id; }

    public Operation getOperation(){ return this.operation; }

    public enum Operation{
        CREATED,
        UPDATED,
        DELETED
    }

}
