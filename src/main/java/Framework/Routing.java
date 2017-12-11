package Framework;

import Framework.Exception.RouteDuplicateException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Routing extends ContainerAware
{
    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayList<String> routeSignatures = new ArrayList<>();

    public void addRoute(Route route) throws RouteDuplicateException
    {
        if(routeSignatures.contains(route.toString())) {
            throw new RouteDuplicateException(route);
        }

        routes.add(route);
        routeSignatures.add(route.toString());
    }

    public void addAction(Method method, String path, ActionInterface handler) throws RouteDuplicateException
    {
        addRoute(new Route(method, path, handler));
    }

    public void get(String path, ActionInterface handler) throws RouteDuplicateException
    {
        addAction(Method.GET, path, handler);
    }

    public void post(String path, ActionInterface handler) throws RouteDuplicateException
    {
        addAction(Method.POST, path, handler);
    }

    public void put(String path, ActionInterface handler) throws RouteDuplicateException
    {
        addAction(Method.PUT, path, handler);
    }

    public void patch(String path, ActionInterface handler) throws RouteDuplicateException
    {
        addAction(Method.PATCH, path, handler);
    }

    public void delete(String path, ActionInterface handler) throws RouteDuplicateException
    {
        addAction(Method.DELETE, path, handler);
    }

    public Route match(HTTPSession session)
    {
        for (Route route : routes) {
            if (session.getMethod().toString().equals(route.getMethod().toString())) {
                Pattern r = Pattern.compile(route.getPath().pattern);

                Matcher m = r.matcher(session.getUri());

                if (m.matches()) {
                    return route;
                }
            }
        }

        return null;
    }
}
