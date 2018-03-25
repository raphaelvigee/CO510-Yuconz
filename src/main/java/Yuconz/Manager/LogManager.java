package Yuconz.Manager;

import Yuconz.Entity.Log;
import Yuconz.Entity.User;
import Yuconz.Model.LogType;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Authentication.AuthenticationManager;
import com.sallyf.sallyf.Container.ServiceInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;

/**
 * Service for log management
 */
public class LogManager implements ServiceInterface
{
    private Hibernate hibernate;
    private YuconzAuthenticationManager authenticationManager;

    /**
     * New LogManager
     * @param hibernate The Hibernate itself.
     */
    public LogManager(Hibernate hibernate)
    {
        this.hibernate = hibernate;
    }

    public void setAuthenticationManager(YuconzAuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Log a message to the database.
     * @param user user to log
     * @param ip ip address to log
     * @param type log type
     * @param details details of action being logged
     */
    public void log(User user, String ip, LogType type, String details)
    {
        Session session = hibernate.getCurrentSession();

        details = details != null ? details : "";
        if (authenticationManager.getCurrentRole() != null) {
            details += details.equals("") ? "" : "; ";
            details += "current_role=" + authenticationManager.getCurrentRole().getName();
        }

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

    /**
     * Log a message to the database.
     * @param user user to log
     * @param ip ip address to log
     * @param type log type
     */
    public void log(User user, String ip, LogType type)
    {
        log(user, ip, type, null);
    }

    /**
     * Log a message to the database.
     * @param ip ip address to log
     * @param type log type
     * @param details details of action being loggednor
     */
    public void log(String ip, LogType type, String details)
    {
        log(null, ip, type, details);
    }

    /**
     * Log a message to the database.
     * @param ip ip address to log
     * @param type log type
     */
    public void log(String ip, LogType type)
    {
        log(null, ip, type, null);
    }
}
