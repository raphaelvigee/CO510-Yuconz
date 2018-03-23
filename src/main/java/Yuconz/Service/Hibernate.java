package Yuconz.Service;

import Yuconz.Entity.InitialEmploymentDetailsRecord;
import Yuconz.Entity.Section;
import Yuconz.Entity.User;
import Yuconz.Model.UserRole;
import com.github.javafaker.Faker;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to wrap Hibernate session factory and event dispatcher.
 */
public class Hibernate implements ServiceInterface
{
    private SessionFactory sessionFactory;

    private EventDispatcher eventDispatcher;

    /**
     * New Hibernate wrapper.
     *
     * @param eventDispatcher the eventDispatcher
     */
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
    }

    /**
     * Initialise the container, to be called after the service is ready.
     *
     * @param container the container
     */
    @Override
    public void initialize(Container container)
    {
        eventDispatcher.register(KernelEvents.POST_MATCH_ROUTE, (et, e) -> getSessionFactory().openSession());
        eventDispatcher.register(KernelEvents.PRE_SEND_RESPONSE, (et, e) -> getSessionFactory().getCurrentSession().close());

        populate();
    }

    /**
     * Gets the SessionFactory.
     *
     * @return
     */
    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    /**
     * Gets the CurrentSession.
     *
     * @return
     */
    public Session getCurrentSession()
    {
        return getSessionFactory().getCurrentSession();
    }

    /**
     * Populates database with dummy data, for debugging.
     * TODO: Remove for production release.
     */
    private void populate()
    {
        populateUsers();
    }

    /**
     * Populate's database with dummy users, for debugging.
     * TODO: Remove for production release.
     */
    private void populateUsers()
    {
        Session session = getSessionFactory().openSession();

        List<User> users = new ArrayList<>();

        if (isEmpty(User.class)) {
            User employee = User.bulk();
            employee.setEmail("employee@yuconz");
            employee.setFirstName("John");
            employee.setLastName("Doe");
            employee.setRole(UserRole.EMPLOYEE);
            employee.setSection(Section.FRONT_END);
            users.add(employee);

            User hr_employee = User.bulk();
            hr_employee.setEmail("hr_employee@yuconz");
            hr_employee.setFirstName("John");
            hr_employee.setLastName("Doe HR");
            hr_employee.setRole(UserRole.EMPLOYEE);
            hr_employee.setSection(Section.RECRUITMENT);
            users.add(hr_employee);

            User manager = User.bulk();
            manager.setEmail("manager@yuconz");
            manager.setFirstName("Mana");
            manager.setLastName("Ger");
            manager.setRole(UserRole.MANAGER);
            manager.setSection(Section.FRONT_END);
            users.add(manager);

            User hr_manager = User.bulk();
            hr_manager.setEmail("hr_manager@yuconz");
            hr_manager.setFirstName("Mana");
            hr_manager.setLastName("Ger HR");
            hr_manager.setRole(UserRole.MANAGER);
            hr_manager.setSection(Section.INTERNAL);
            users.add(hr_manager);

            User director = User.bulk();
            director.setEmail("director@yuconz");
            director.setFirstName("Roman");
            director.setLastName("Miles");
            director.setRole(UserRole.DIRECTOR);
            director.setSection(Section.DIRECTORATE);
            users.add(director);

            Transaction transaction = session.beginTransaction();

            Faker faker = Faker.instance();

            for (int i = 0; i < 20; i++) {
                users.add(User.bulk());
            }

            for (User user : users) {
                InitialEmploymentDetailsRecord ied = new InitialEmploymentDetailsRecord();
                ied.setUser(user);
                ied.setStartDate(LocalDate.now().minusYears(1));
                ied.setInterviewNotes(faker.lorem().sentence());

                session.persist(user);
                session.persist(ied);
            }

            transaction.commit();
        }

        session.close();
    }

    /**
     * Returns true if a class (table) is empty, else false.
     *
     * @param aClass Class (table) to test
     * @param <T>    data type
     * @return boolean
     */
    private <T> boolean isEmpty(Class<T> aClass)
    {
        Session session = getCurrentSession();

        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("SELECT COUNT(*) FROM " + aClass.getSimpleName());

        Long count = (Long) query.getSingleResult();
        transaction.commit();

        return count == 0;
    }
}
