package Yuconz.Service;

import Yuconz.Entity.User;
import Yuconz.Model.Role;
import com.sallyf.sallyf.Container.ServiceInterface;
import com.sallyf.sallyf.Exception.FrameworkException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class Hibernate implements ServiceInterface
{
    private SessionFactory sessionFactory;

    public Hibernate()
    {
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();

            Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Throwable e) {
            throw new FrameworkException(e);
        }

        getSessionFactory().openSession();

        populateUsers();
    }

    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public Session getCurrentSession()
    {
        return getSessionFactory().openSession();
    }

    private <T> boolean isEmpty(Class<T> aClass)
    {
        Session session = getCurrentSession();

        Query query = session.createQuery("SELECT COUNT(*) FROM " + aClass.getSimpleName());

        Long count = (Long) query.getSingleResult();

        return count == 0;
    }

    private void populateUsers()
    {
        Session session = getCurrentSession();

        if (isEmpty(User.class)) {
            User employee = new User();
            employee.setUsername("employee@yuconz");
            employee.setFirstName("John");
            employee.setLastName("Doe");
            employee.setPassword(User.hash("123"));
            employee.addRole(Role.EMPLOYEE);

            User manager = new User();
            manager.setUsername("manager@yuconz");
            manager.setFirstName("Mana");
            manager.setLastName("Ger");
            manager.setPassword(User.hash("123"));
            manager.addRole(Role.EMPLOYEE);
            manager.addRole(Role.MANAGER);

            User director = new User();
            director.setUsername("director@yuconz");
            director.setFirstName("Roman");
            director.setLastName("Miles");
            director.setPassword(User.hash("123"));
            director.addRole(Role.DIRECTOR);

            Transaction transaction = session.beginTransaction();

            session.persist(employee);
            session.persist(manager);
            session.persist(director);

            transaction.commit();
        }
    }
}
