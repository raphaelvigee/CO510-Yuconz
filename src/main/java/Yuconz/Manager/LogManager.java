package Yuconz.Manager;

import Yuconz.Entity.Log;
import Yuconz.Entity.User;
import Yuconz.Model.LogType;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Container.ServiceInterface;
import com.sallyf.sallyf.Server.RuntimeBag;
import com.sallyf.sallyf.Server.RuntimeBagContext;
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
     *
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
     *
     * @param user    user to log
     * @param type    log type
     * @param details details of action being logged
     */
    public void log(User user, LogType type, String details)
    {
        RuntimeBag runtimeBag = RuntimeBagContext.get();

        Session session = hibernate.getCurrentSession();

        Log log = new Log();
        log.setUser(user);
        log.setTime(new Date());
        log.setIp(runtimeBag.getRequest().getRemoteAddr());
        log.setLogType(type);
        log.setDetails(details);
        log.setLoginRole(authenticationManager.getCurrentRole());

        Transaction transaction = session.beginTransaction();
        session.persist(log);
        transaction.commit();
    }
}
