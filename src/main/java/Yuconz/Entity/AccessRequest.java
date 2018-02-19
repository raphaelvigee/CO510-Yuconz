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

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public AbstractRecord getRecord()
    {
        return record;
    }

    public void setRecord(AbstractRecord record)
    {
        this.record = record;
    }

    public User getRequestor()
    {
        return requestor;
    }

    public void setRequestor(User requestor)
    {
        this.requestor = requestor;
    }

    public User getModerator()
    {
        return moderator;
    }

    public void setModerator(User moderator)
    {
        this.moderator = moderator;
    }

    public boolean isAccepted()
    {
        return accepted;
    }

    public void setAccepted(boolean accepted)
    {
        this.accepted = accepted;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }
}
