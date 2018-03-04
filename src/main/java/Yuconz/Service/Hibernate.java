package Yuconz.Service;

import Yuconz.Entity.Section;
import Yuconz.Entity.User;
import Yuconz.Model.UserRole;
import com.sallyf.sallyf.Container.Container;
import com.sallyf.sallyf.Container.ServiceInterface;
import com.sallyf.sallyf.EventDispatcher.EventDispatcher;
import com.sallyf.sallyf.Exception.FrameworkException;
import com.sallyf.sallyf.KernelEvents;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.concurrent.atomic.AtomicReference;

public class Hibernate implements ServiceInterface
{
    private SessionFactory sessionFactory;

    private EventDispatcher eventDispatcher;

    public Hibernate(EventDispatcher eventDispatcher)
    {
        this.eventDispatcher = eventDispatcher;
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

    @Override
    public void initialize(Container container)
    {
        AtomicReference<Session> session = new AtomicReference<>();

        eventDispatcher.register(KernelEvents.POST_MATCH_ROUTE, (et, e) -> session.set(getSessionFactory().openSession()));
        eventDispatcher.register(KernelEvents.PRE_SEND_RESPONSE, (et, e) -> session.get().close());
    }

    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public Session getCurrentSession()
    {
        return getSessionFactory().getCurrentSession();
    }

    private <T> boolean isEmpty(Class<T> aClass)
    {
        Session session = getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("SELECT COUNT(*) FROM " + aClass.getSimpleName());

        Long count = (Long) query.getSingleResult();
        transaction.commit();

        return count == 0;
    }

    private void populateUsers()
    {
        Session session = getCurrentSession();

        if (isEmpty(User.class)) {
            User employee = User.bulk();
            employee.setEmail("employee@yuconz");
            employee.setFirstName("John");
            employee.setLastName("Doe");
            employee.setRole(UserRole.EMPLOYEE);
            employee.setSection(Section.RECRUITMENT);

            User hr_employee = User.bulk();
            hr_employee.setEmail("hr_employee@yuconz");
            hr_employee.setFirstName("John");
            hr_employee.setLastName("Doe HR");
            hr_employee.setRole(UserRole.EMPLOYEE);
            hr_employee.setSection(Section.RECRUITMENT);

            User manager = User.bulk();
            manager.setEmail("manager@yuconz");
            manager.setFirstName("Mana");
            manager.setLastName("Ger");
            manager.setRole(UserRole.MANAGER);
            manager.setSection(Section.FRONT_END);

            User hr_manager = User.bulk();
            hr_manager.setEmail("hr_manager@yuconz");
            hr_manager.setFirstName("Mana");
            hr_manager.setLastName("Ger HR");
            hr_manager.setRole(UserRole.MANAGER);
            hr_manager.setSection(Section.INTERNAL);

            User director = User.bulk();
            director.setEmail("director@yuconz");
            director.setFirstName("Roman");
            director.setLastName("Miles");
            director.setRole(UserRole.DIRECTOR);
            director.setSection(Section.DIRECTORATE);

            Transaction transaction = session.beginTransaction();

            for (int i = 0; i < 20; i++) {
                session.persist(User.bulk());
            }

            session.persist(employee);
            session.persist(hr_employee);
            session.persist(manager);
            session.persist(hr_manager);
            session.persist(director);

            transaction.commit();
        }
    }
}
