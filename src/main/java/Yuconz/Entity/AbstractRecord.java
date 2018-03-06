package Yuconz.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Entity for an abstract record, base implementation for all record types.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractRecord
{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid2"
    )
    private String id;

    @ManyToOne(optional = false)
    private User user;

    private LocalDate createdAt = LocalDate.now();

    @OneToMany(mappedBy = "record")
    private Set<AccessRequest> accessRequests;

    /**
     * Get the ID.
     *
     * @return id
     */
    public String getId()
    {
        return id;
    }

    /**
     * Set the ID.
     *
     * @param id id
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Get the user.
     *
     * @return user
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Set the user.
     *
     * @param user user
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * Get the created at date.
     *
     * @return createdAt
     */
    public LocalDate getCreatedAt()
    {
        return createdAt;
    }

    /**
     * Set the created at date.
     *
     * @param createdAt createdAt
     */
    public void setCreatedAt(LocalDate createdAt)
    {
        this.createdAt = createdAt;
    }

    /**
     * Get the access requests.
     *
     * @return accessRequests
     */
    public Set<AccessRequest> getAccessRequests()
    {
        return accessRequests;
    }

    /**
     * Set access requests.
     *
     * @param accessRequests accessRequests
     */
    public void setAccessRequests(Set<AccessRequest> accessRequests)
    {
        this.accessRequests = accessRequests;
    }

    abstract public String getTitle();
}
