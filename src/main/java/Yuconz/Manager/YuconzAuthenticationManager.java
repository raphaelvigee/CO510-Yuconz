package Yuconz.Manager;

import Yuconz.Entity.User;
import Yuconz.Model.Role;
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
import org.eclipse.jetty.server.Request;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class YuconzAuthenticationManager extends AuthenticationManager
{
    private final Hibernate hibernate;

    public YuconzAuthenticationManager(Container container, Router router, EventDispatcher eventDispatcher, ExpressionLanguage expressionLanguage, Hibernate hibernate)
    {
        super(container, new Configuration(), router, eventDispatcher, expressionLanguage);
        this.hibernate = hibernate;
    }

    @Override
    public void initialize(Container container)
    {
        super.initialize(container);

        container.add(new ServiceDefinition(YuconzAuthenticationVoter.class)).addTag("authentication.voter");

        container.getServiceDefinitions()
                .entrySet()
                .removeIf(pair -> pair.getValue().getClass().equals(AuthenticationVoter.class));
    }

    public UserInterface authenticate(Request request, String username, String password, String roleStr)
    {
        Role role = Role.valueOf(roleStr.toUpperCase());

        Session session = hibernate.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);

        Root<User> root = query.from(User.class);
        query.select(root)
                .where(
                        builder.equal(root.get("username"), username),
                        builder.equal(root.get("password"), User.hash(password))
                );

        Query<User> q = session.createQuery(query);

        List<User> users = q.getResultList();

        User user;
        if (users.isEmpty()) {
            user = null;
        } else {
            user = users.get(0);

            if (!user.getRoles().contains(role)) {
                user = null;
            }
        }

        request.getSession(true).setAttribute("user", user);

        return user;
    }

    public void logout(Request request)
    {
        request.getSession(true).setAttribute("user", null);
    }
}
