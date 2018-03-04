package Yuconz.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class AccessRequest
{
    @Id
    private String id;

    @ManyToOne
    private AbstractRecord record;

    @ManyToOne(optional = false)
    private User requestor;

    @ManyToOne
    private User moderator;

    private boolean accepted;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /**
     * Get the ID.
     * @return ID
     */
    public String getId()
    {
        return id;
    }

    /**
     * Set the ID.
     * @param id ID
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Get the record.
     * @return record.
     */
    public AbstractRecord getRecord()
    {
        return record;
    }

    /**
     * Set the record.
     * @param record record.
     */
    public void setRecord(AbstractRecord record)
    {
        this.record = record;
    }

    /**
     * Get the requestor.
     * @return requestor.
     */
    public User getRequestor()
    {
        return requestor;
    }

    /**
     * Set the requestor.
     * @param requestor requestor
     */
    public void setRequestor(User requestor)
    {
        this.requestor = requestor;
    }

    /**
     * get the moderator.
     * @return moderator
     */
    public User getModerator()
    {
        return moderator;
    }

    /**
     *  Set the moderator.
     * @param moderator moderator
     */
    public void setModerator(User moderator)
    {
        this.moderator = moderator;
    }

    /**
     * Is the user authorised to view a document.
     * @return True or false
     */
    public boolean isAccepted()
    {
        return accepted;
    }

    /**
     * Set whether the user is authorised to view a document.
     * @param accepted
     */
    public void setAccepted(boolean accepted)
    {
        this.accepted = accepted;
    }

    /**
     * Get the date it was created at.
     * @return createdAt
     */
    public Date getCreatedAt()
    {
        return createdAt;
    }

    /**
     * Set the created at date.
     * @param createdAt createdAt
     */
    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }
}
