package Yuconz.Manager;

import Yuconz.Entity.Log;
import Yuconz.Entity.User;
import Yuconz.Model.LogType;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Container.ServiceInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;

public class LogManager implements ServiceInterface
{

    private Hibernate hibernate;

    public LogManager(Hibernate hibernate)
    {

        this.hibernate = hibernate;
    }

    public void log(User user, String ip, LogType type, String details)
    {
        Session session = hibernate.getCurrentSession();

        Log log = new Log();
        log.setUser(user);
        log.setTime(new Date());
        log.setIp(ip);
        log.setLogType(type);
        log.setDetails(details);

        Transaction transaction = session.beginTransaction();
        session.persist(log);
        transaction.commit();
    }

    public void log(User user, String ip, LogType type)
    {
        log(user, ip, type, null);
    }

    public void log(String ip, LogType type, String details)
    {
        log(null, ip, type, details);
    }

    public void log(String ip, LogType type)
    {
        log(null, ip, type, null);
    }

}
