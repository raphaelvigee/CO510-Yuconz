package Yuconz.Entity;

import Yuconz.Model.LogType;
import Yuconz.Model.Role;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "log")
public class Log
{
    private Integer id;

    private User user;

    private Date timestamp;

    private String ip;

    private LogType logType;

    private String details;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @OneToOne
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

    @Enumerated(EnumType.STRING)
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
