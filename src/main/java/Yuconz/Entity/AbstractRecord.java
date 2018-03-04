package Yuconz.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractRecord
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(optional = false)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "record")
    private Set<AccessRequest> accessRequests;

    /**
     * Get the ID.
     * @return id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * Set the ID.
     * @param id id
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * Get the user.
     * @return user
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Set the user.
     * @param user user
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * Get the created at date.
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

    /**
     * Get the access requests.
     * @return accessRequests
     */
    public Set<AccessRequest> getAccessRequests()
    {
        return accessRequests;
    }

    /**
     * Set access requests.
     * @param accessRequests accessRequests
     */
    public void setAccessRequests(Set<AccessRequest> accessRequests)
    {
        this.accessRequests = accessRequests;
    }
}
