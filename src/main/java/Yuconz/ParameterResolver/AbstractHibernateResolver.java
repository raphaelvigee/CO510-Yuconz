package Yuconz.ParameterResolver;

import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Exception.HttpException;
import com.sallyf.sallyf.Router.RouteParameterResolverInterface;
import com.sallyf.sallyf.Server.Status;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Resolver for fetching object from database based on route parameter.
 */
public abstract class AbstractHibernateResolver implements RouteParameterResolverInterface<Object>
{
    private Hibernate hibernate;

    public AbstractHibernateResolver(Hibernate hibernate)
    {
        this.hibernate = hibernate;
    }

    /**
     * Resolve entity from database based on route parameter for user in path.
     *
     * @param name  name of parameter from path
     * @param value value of parameter from path
     * @return Object from database
     */
    @Override
    public Object resolve(String name, String value)
    {
        Session session = hibernate.getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Object entity = session.find(getEntityClass(), value);
        transaction.commit();

        if (entity == null) {
            throw new HttpException(Status.NOT_FOUND);
        }

        return entity;
    }

    public abstract Class getEntityClass();
}
