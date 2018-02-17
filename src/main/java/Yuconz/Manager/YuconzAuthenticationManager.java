package Yuconz.Manager;

import Yuconz.Model.Role;
import Yuconz.Model.User;
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

public class YuconzAuthenticationManager extends AuthenticationManager
{
    public YuconzAuthenticationManager(Router router, EventDispatcher eventDispatcher, ExpressionLanguage expressionLanguage)
    {
        super(new Configuration(), router, eventDispatcher, expressionLanguage);
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

    public UserInterface authenticate(Request request, String username, String password, String role)
    {
        // if username && password && role in database

        User user = new User(username, password, Role.valueOf(role.toUpperCase()));

        request.getSession(true).setAttribute("user", user);

        return user;
    }

    public void logout(Request request)
    {
        request.getSession(true).setAttribute("user", null);
    }
}
