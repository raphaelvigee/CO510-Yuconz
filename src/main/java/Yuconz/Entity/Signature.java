package Yuconz.Entity;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entity for user signatures.
 */
@Entity
@Table
public class Signature
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate date = LocalDate.now();

    @ManyToOne
    private User user;

    public Signature()
    {
    }

    public Signature(User user)
    {
        this();

        this.user = user;
    }

    /**
     * Gets Signature's ID
     *
     * @return ID
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * Sets Signature's ID
     *
     * @param id ID
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * Gets Signature's date
     *
     * @return date
     */
    public LocalDate getDate()
    {
        return date;
    }

    /**
     * Sets Signature's date
     *
     * @param date signing date
     */
    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    /**
     * Gets Signature's user
     *
     * @return user
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Sets Signature's user
     *
     * @param user user
     */
    public void setUser(User user)
    {
        this.user = user;
    }
}
