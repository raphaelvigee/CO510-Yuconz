package Yuconz.Generator;

import Yuconz.Entity.User;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * Generator for Yuconz IDs.
 */
public class YuconzIdGenerator implements IdentifierGenerator
{
    /**
     * Generates an ID.
     *
     * @param session session
     * @param o       object
     * @return ID
     * @throws HibernateException
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException
    {
        return User.generateYuconzId();
    }
}
