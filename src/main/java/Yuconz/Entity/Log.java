package Yuconz.Entity;

import Yuconz.Model.LogType;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity for a log entry.
 */
@Entity
@Table
public class Log
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private String ip;

    @Enumerated(EnumType.STRING)
    private LogType logType;

    private String details;

    /**
     * Get Log's ID.
     * @return ID
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * Set a Log's ID.
     * @param id ID
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * Get a User.
     * @return user
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Set a User.
     * @param user user
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * Get the time.
     * @return timestamp
     */
    public Date getTime()
    {
        return timestamp;
    }

    /**
     * Set the time.
     * @param time time
     */
    public void setTime(Date time)
    {
        this.timestamp = time;
    }

    /**
     * Get the IP Address.
     * @return IP Address
     */
    public String getIp()
    {
        return ip;
    }

    /**
     * Set the IP Address.
     * @param ip IP Address
     */
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    /**
     * Get the Log type.
     * @return logType
     */
    public LogType getLogType()
    {
        return logType;
    }

    /**
     * Set the Log type.
     * @param logType logType
     */
    public void setLogType(LogType logType)
    {
        this.logType = logType;
    }

    /**
     * Get details about a Log.
     * @return details
     */
    public String getDetails()
    {
        return details;
    }

    /**
     * Sets details about a Log.
     * @param details details
     */
    public void setDetails(String details)
    {
        this.details = details;
    }
}
