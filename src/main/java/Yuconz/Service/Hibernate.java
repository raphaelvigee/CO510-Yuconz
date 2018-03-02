package Yuconz.Service;

import Yuconz.Entity.Address;
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
            employee.setEmail("employee@yuconz");
            employee.setFirstName("John");
            employee.setLastName("Doe");
            employee.setPassword(User.hash("123"));
            employee.setRole(Role.EMPLOYEE);
            employee.setAddress(new Address());

            User hr_employee = new User();
            hr_employee.setEmail("hr_employee@yuconz");
            hr_employee.setFirstName("John");
            hr_employee.setLastName("Doe HR");
            hr_employee.setPassword(User.hash("123"));
            hr_employee.setRole(Role.HR_EMPLOYEE);
            hr_employee.setAddress(new Address());

            User manager = new User();
            manager.setEmail("manager@yuconz");
            manager.setFirstName("Mana");
            manager.setLastName("Ger");
            manager.setPassword(User.hash("123"));
            manager.setRole(Role.MANAGER);
            manager.setAddress(new Address());

            User director = new User();
            director.setEmail("director@yuconz");
            director.setFirstName("Roman");
            director.setLastName("Miles");
            director.setPassword(User.hash("123"));
            director.setRole(Role.DIRECTOR);
            director.setAddress(new Address());

            Transaction transaction = session.beginTransaction();

            session.persist(employee);
            session.persist(hr_employee);
            session.persist(manager);
            session.persist(director);

            transaction.commit();
        }
    }
}
