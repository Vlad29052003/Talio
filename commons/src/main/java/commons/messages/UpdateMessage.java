package commons.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public abstract class UpdateMessage {

    private long id;
    private Operation operation;

    /**
     * Empty constructor for object mappers.
     */
    public UpdateMessage(){}

    /**
     * Create a new {@link UpdateMessage UpdateMessage}
     *
     * @param id of the to be updated object
     * @param operation of the update
     */
    public UpdateMessage(long id, Operation operation){
        this.id = id;
        this.operation = operation;
    }

    /**
     * @return The updated object
     */
    @JsonIgnore
    public abstract Object getObject();

    /**
     * @param object to be updated
     */
    public abstract void setObject(Object object);

    public long getId(){ return this.id; }

    public Operation getOperation(){ return this.operation; }

    public enum Operation{
        CREATED,
        UPDATED,
        DELETED
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

}
