package Yuconz.ParameterResolver;

import Yuconz.Entity.User;
import Yuconz.Service.Hibernate;

/**
 * Resolver for fetching user from database based on route parameter for user in path.
 */
public class UserResolver extends AbstractHibernateResolver
{
    /**
     * New UserResolver.
     *
     * @param hibernate The hibernate itself.
     */
    public UserResolver(Hibernate hibernate)
    {
        super(hibernate);
    }

    @Override
    public Class getEntityClass()
    {
        return User.class;
    }
}
