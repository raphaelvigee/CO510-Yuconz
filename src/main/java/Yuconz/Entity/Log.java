package Yuconz.Entity;

import Yuconz.Model.LogType;

import javax.persistence.*;
import java.util.Date;

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

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Date getTime()
    {
        return timestamp;
    }

    public void setTime(Date time)
    {
        this.timestamp = time;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public LogType getLogType()
    {
        return logType;
    }

    public void setLogType(LogType logType)
    {
        this.logType = logType;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }
}
