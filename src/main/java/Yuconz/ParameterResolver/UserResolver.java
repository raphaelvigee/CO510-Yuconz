package Yuconz.ParameterResolver;

import Yuconz.Entity.User;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Exception.HttpException;
import com.sallyf.sallyf.Router.RouteParameterResolverInterface;
import com.sallyf.sallyf.Server.RuntimeBag;
import com.sallyf.sallyf.Server.Status;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Resolver for fetching user from database based on route parameter for user in path.
 */
public class UserResolver implements RouteParameterResolverInterface<User>
{
    private Hibernate hibernate;

    /**
     * New UserResolver.
     * @param hibernate The hibernate itself.
     */
    public UserResolver(Hibernate hibernate)
    {
        this.hibernate = hibernate;
    }

    /**
     * Resolve user from database based on route parameter for user in path.
     * @param name name of parameter from path
     * @param value value of parameter from path
     * @param runtimeBag The runtimeBag itself.
     * @return User from database
     */
    @Override
    public User resolve(String name, String value)
    {
        Session session = hibernate.getCurrentSession();

        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, value);
        transaction.commit();

        if (user == null) {
            throw new HttpException(Status.NOT_FOUND);
        }

        return user;
    }
}
