package Yuconz.Entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity for user signatures.
 */
@Entity
@Table
public class Signature
{
    @Id
    private String id;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    private User user;

    /**
     * Gets Signature's ID
     * @return ID
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets Signature's ID
     * @param id ID
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Gets  Signature's name
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets Signature's name
     * @param name full name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets Signature's date
     * @return date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * Sets Signature's date
     * @param date signing date
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * Gets Signature's user
     * @return user
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Sets Signature's user
     * @param user user
     */
    public void setUser(User user)
    {
        this.user = user;
    }
}
