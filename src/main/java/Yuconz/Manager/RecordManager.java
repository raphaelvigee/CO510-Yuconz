package Yuconz.Manager;

import Yuconz.Entity.AbstractRecord;
import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Entity.User;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Container.ServiceInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Collectors;

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

        records = records.stream().filter(r -> {
            if (r instanceof AnnualReviewRecord) {
                return ((AnnualReviewRecord) r).isAccepted();
            }

            return true;
        }).collect(Collectors.toList());

        return records;
    }
}
