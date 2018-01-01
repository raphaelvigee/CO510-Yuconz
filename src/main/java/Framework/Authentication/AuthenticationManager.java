package Framework.Authentication;

import Framework.Authentication.Annotation.Security;
import Framework.Authentication.Voter.VoterInterface;
import Framework.Container.Container;
import Framework.Container.ContainerAware;
import Framework.EventDispatcher.EventDispatcher;
import Framework.Exception.HttpException;
import Framework.KernelEvents;
import Framework.Router.ActionParameterResolver.UserInterfaceResolver;
import Framework.Router.Route;
import Framework.Router.Router;
import Framework.Server.RuntimeBag;
import Framework.Server.Status;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class AuthenticationManager extends ContainerAware
{
    ArrayList<UserDataSourceInterface> dataSources = new ArrayList<>();

    HashMap<Route, ArrayList<VoterInterface>> securedRoutes = new HashMap<>();

    public AuthenticationManager(Container container)
    {
        super(container);
    }

    public void initialize()
    {
        getContainer().get(Router.class).addActionParameterResolver(new UserInterfaceResolver(getContainer()));

        EventDispatcher eventDispatcher = getContainer().get(EventDispatcher.class);

        eventDispatcher.register(KernelEvents.ROUTE_REGISTER, (et, routeRegisterEvent) -> {
            Method method = routeRegisterEvent.getMethod();

            Security annotation = method.getAnnotation(Security.class);

            if (null == annotation) {
                return;
            }

            Class<? extends VoterInterface>[] voterClasses = annotation.value();

            ArrayList<VoterInterface> voterInstances = new ArrayList<>();

            for (Class<? extends VoterInterface> voterClass : voterClasses) {
                VoterInterface voter;
                try {
                    voter = voterClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }

                voterInstances.add(voter);
            }

            securedRoutes.put(routeRegisterEvent.getRoute(), voterInstances);
        });

        eventDispatcher.register(KernelEvents.POST_MATCH_ROUTE, (et1, routeMatchEvent) -> {
            Route route = routeMatchEvent.getRuntimeBag().getRoute();
            if (!vote(route, routeMatchEvent.getRuntimeBag())) {
                route.setHandler(rb -> {
                    throw new HttpException(Status.FORBIDDEN);
                });
            }
        });
    }

    public boolean vote(Route route, RuntimeBag runtimeBag)
    {
        if (securedRoutes.containsKey(route)) {
            AuthenticationManager authenticationManager = getContainer().get(AuthenticationManager.class);

            ArrayList<VoterInterface> voters = securedRoutes.get(route);
            UserInterface user = authenticationManager.getUser(runtimeBag);

            for (VoterInterface voter : voters) {
                if (!voter.test(getContainer(), user, runtimeBag)) {
                    return false;
                }
            }
        }

        return true;
    }

    public UserInterface authenticate(Request request, String username, String password) throws AuthenticationException
    {
        return authenticate(request, username, password, null);
    }

    public UserInterface authenticate(Request request, String username, String password, Class<? extends UserDataSourceInterface> dataSourceClass) throws AuthenticationException
    {
        if (dataSources.size() == 0) {
            throw new AuthenticationException("No datasource provided");
        }

        UserDataSourceInterface dataSource;

        if (dataSourceClass == null) {
            if (dataSources.size() == 1) {
                dataSource = dataSources.get(0);
            } else {
                throw new AuthenticationException("Ambiguous datasource");
            }
        } else {
            dataSource = getDataSource(dataSourceClass);
        }

        UserInterface user = dataSource.getUser(username, password);

        request.getSession(true).setAttribute("user", user);

        return user;
    }

    public void addDataSource(UserDataSourceInterface ds)
    {
        dataSources.add(ds);
    }

    public ArrayList<UserDataSourceInterface> getDataSources()
    {
        return dataSources;
    }

    public UserDataSourceInterface getDataSource(Class<? extends UserDataSourceInterface> dataSourceClass) throws AuthenticationException
    {
        for (UserDataSourceInterface dataSource : dataSources) {
            if (dataSource.getClass().equals(dataSourceClass)) {
                return dataSource;
            }
        }

        throw new AuthenticationException("No datasource found for class: " + dataSourceClass);
    }

    public UserInterface getUser(RuntimeBag runtimeBag)
    {
        Request request = runtimeBag.getRequest();

        HttpSession session = request.getSession();

        if (null == session) {
            return null;
        }

        return (UserInterface) session.getAttribute("user");
    }
}
