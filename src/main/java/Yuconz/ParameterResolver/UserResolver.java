package Yuconz.ParameterResolver;

import Yuconz.Entity.User;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Exception.HttpException;
import com.sallyf.sallyf.Router.RouteParameterResolverInterface;
import com.sallyf.sallyf.Server.RuntimeBag;
import com.sallyf.sallyf.Server.Status;
import org.hibernate.Session;

public class UserResolver implements RouteParameterResolverInterface<User>
{
    private Hibernate hibernate;

    public UserResolver(Hibernate hibernate)
    {
        this.hibernate = hibernate;
    }

    @Override
    public User resolve(String name, String value, RuntimeBag runtimeBag)
    {
        Session session = hibernate.getCurrentSession();

        User user = session.find(User.class, value);

        if (user == null) {
            throw new HttpException(Status.NOT_FOUND);
        }

        return user;
    }
}
