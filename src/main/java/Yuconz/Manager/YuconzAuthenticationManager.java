package Yuconz.Manager;

import Yuconz.Entity.User;
import Yuconz.Model.LogType;
import Yuconz.Model.LoginRole;
import Yuconz.Service.Hibernate;
import Yuconz.Voter.YuconzAuthenticationVoter;
import com.sallyf.sallyf.Authentication.AuthenticationManager;
import com.sallyf.sallyf.Authentication.Configuration;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Authentication.Voter.AuthenticationVoter;
import com.sallyf.sallyf.Container.Container;
import com.sallyf.sallyf.Container.ServiceDefinition;
import com.sallyf.sallyf.EventDispatcher.EventDispatcher;
import com.sallyf.sallyf.ExpressionLanguage.ExpressionLanguage;
import com.sallyf.sallyf.Router.Router;
import com.sallyf.sallyf.Server.RuntimeBag;
import com.sallyf.sallyf.Server.RuntimeBagContext;
import org.eclipse.jetty.server.Request;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Authentication Manager for system.
 */
public class YuconzAuthenticationManager extends AuthenticationManager
{
    private final Hibernate hibernate;

    private final LogManager logManager;

    private final AuthorisationManager authorisationManager;

    /**
     * New Authentication Manager
     * @param container the container
     * @param router the router
     * @param eventDispatcher the eventDispatcher
     * @param expressionLanguage the expressionLanguage handler
     * @param hibernate The Hibernate itself.
     * @param logManager the logManager
     * @param authorisationManager the authorisationManager
     */
    public YuconzAuthenticationManager(Container container, Router router, EventDispatcher eventDispatcher, ExpressionLanguage expressionLanguage, Hibernate hibernate, LogManager logManager, AuthorisationManager authorisationManager)
    {
        super(container, new Configuration(), router, eventDispatcher, expressionLanguage);
        this.hibernate = hibernate;
        this.logManager = logManager;
        this.authorisationManager = authorisationManager;
    }

    /**
     * Initialise authentication manager container
     * @param container
     */
    @Override
    public void initialize(Container container)
    {
        super.initialize(container);

        container.add(new ServiceDefinition<>(YuconzAuthenticationVoter.class)).addTag("authentication.voter");

        container.getServiceDefinitions()
                .entrySet()
                .removeIf(pair -> pair.getValue().getClass().equals(AuthenticationVoter.class));
    }

    /**
     * Attempt to authenticate a user given a provided password and role
     * @param request the request
     * @param username user's username
     * @param password user's password
     * @param roleStr requested role
     * @return new UserInterface for provided input
     */
    public UserInterface authenticate(Request request, String username, String password, String roleStr)
    {
        // Get requested LoginRole
        LoginRole loginRole = LoginRole.valueOf(roleStr.toUpperCase());

        // Try finding user in DB
        Session session = hibernate.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        Root<User> root = query.from(User.class);
        query.select(root)
                .where(
                        builder.equal(root.get("email"), username),
                        builder.equal(root.get("password"), User.hash(password))
                );

        Query<User> q = session.createQuery(query);

        List<User> users = q.getResultList();
        transaction.commit();

        User user;
        String logDetails = null;

        // No user found
        if (users.isEmpty()) {
            user = null;
            loginRole = null;
            logDetails = "user not found";
        } else {
            // User found
            user = users.get(0);

            // Check if requested role is allowed
            if (!authorisationManager.hasUserRights(user, loginRole)) {
                user = null;
                loginRole = null;
                logDetails = "invalid role";
            }
        }

        // Persist data to session
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("user", user);
        httpSession.setAttribute("role", loginRole);

        logManager.log(user, user == null ? LogType.AUTHENTICATION_LOGIN_FAIL : LogType.AUTHENTICATION_LOGIN_SUCCESS, logDetails);

        return user;
    }

    /**
     * Log out a user, invalidating the session.
     * @param request the request
     */
    public void logout(Request request)
    {
        // Clear session data
        HttpSession session = request.getSession(true);
        session.setAttribute("user", null);
        session.setAttribute("role", null);
    }

    /**
     * Gets the current role for a session's request.
     * @return LoginRole for request
     */
    public LoginRole getCurrentRole()
    {
        RuntimeBag runtimeBag = RuntimeBagContext.get();
        Request request = runtimeBag.getRequest();

        HttpSession session = request.getSession(true);

        return (LoginRole) session.getAttribute("role");
    }
}
