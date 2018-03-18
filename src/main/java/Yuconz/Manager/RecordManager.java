package Yuconz.Manager;

import Yuconz.Entity.AbstractRecord;
import Yuconz.Entity.User;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Container.ServiceInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class RecordManager implements ServiceInterface
{
    private Hibernate hibernate;

    public RecordManager(Hibernate hibernate)
    {
        this.hibernate = hibernate;
    }

    public List<AbstractRecord> getRecords(User user)
    {
        Session session = hibernate.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from AbstractRecord r where user = :user")
                .setParameter("user", user);

        List<AbstractRecord> records = query.getResultList();

        transaction.commit();

        return records;
    }

    public <T extends AbstractRecord> T getLastRecord(User user, Class<T> type)
    {
        Session session = hibernate.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from " + type.getSimpleName() + " r where user = :user")
                .setMaxResults(1)
                .setParameter("user", user);

        T record;
        try {
            record = (T) query.getSingleResult();
        } catch (NoResultException e) {
            record = null;
        }

        transaction.commit();

        return record;
    }
}
