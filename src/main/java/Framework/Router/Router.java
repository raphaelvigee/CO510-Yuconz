package Framework.Router;

import Framework.BaseController;
import Framework.Container.Container;
import Framework.Container.ContainerAware;
import Framework.EventDispatcher.EventDispatcher;
import Framework.Exception.FrameworkException;
import Framework.Exception.RouteDuplicateException;
import Framework.Exception.UnhandledParameterException;
import Framework.Router.Event.PreInvokeActionEvent;
import Framework.Server.HTTPSession;
import Framework.Server.Method;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router extends ContainerAware
{
    private ArrayList<Route> routes = new ArrayList<>();

    private ArrayList<String> routeSignatures = new ArrayList<>();

    public void addController(Class<? extends BaseController> controllerClass) throws FrameworkException
    {
        EventDispatcher eventDispatcher = getContainer().get(EventDispatcher.class);

        Framework.Annotation.Route controllerAnnotation = controllerClass.getAnnotation(Framework.Annotation.Route.class);

        String pathPrefix = controllerAnnotation == null ? "" : controllerAnnotation.path();

        java.lang.reflect.Method[] methods = controllerClass.getMethods();

        for (java.lang.reflect.Method method : methods) {
            if (method.isAnnotationPresent(Framework.Annotation.Route.class)) {
                if (!java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
                    System.err.println("Method `" + method.getName() + "` is not static, ignoring");
                    continue;
                }

                Framework.Annotation.Route routeAnnotation = method.getAnnotation(Framework.Annotation.Route.class);

                final Class<?>[] parameterTypes = method.getParameterTypes();

                addAction(routeAnnotation.method(), pathPrefix + routeAnnotation.path(), (session) -> {
                    Object[] parameters = getActionParameters(parameterTypes, session);

                    ActionInvokerInterface actionInvoker = () -> {
                        try {
                            return (Response) method.invoke(null, parameters);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            return null;
                        }
                    };

                    PreInvokeActionEvent preInvokeActionEvent = new PreInvokeActionEvent(session, parameters, actionInvoker);

                    eventDispatcher.dispatch(Events.PRE_INVOKE_ACTION, preInvokeActionEvent);

                    return preInvokeActionEvent.getActionInvoker().invoke();
                });
            }
        }
    }

    public Object[] getActionParameters(Class<?>[] parameterTypes, HTTPSession session) throws UnhandledParameterException
    {
        Object[] parameters = new Object[parameterTypes.length];
        int i = 0;
        for (Class<?> parameterType : parameterTypes) {
            Object p;

            if (parameterType == Container.class) {
                p = getContainer();
            } else if (parameterType == HTTPSession.class) {
                p = session;
            } else if (parameterType == RouteParameters.class) {
                p = getRouteParameters(session.getRoute(), session);
            } else {
                throw new UnhandledParameterException(parameterType);
            }

            parameters[i++] = p;
        }

        return parameters;
    }

    public void addRoute(Route route) throws RouteDuplicateException
    {
        if (routeSignatures.contains(route.toString())) {
            throw new RouteDuplicateException(route);
        }

        routes.add(route);
        routeSignatures.add(route.toString());
    }

    public void addAction(Method method, String path, ActionWrapperInterface handler) throws RouteDuplicateException
    {
        addRoute(new Route(method, path, handler));
    }

    public ArrayList<Route> getRoutes()
    {
        return routes;
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

    public RouteParameters getRouteParameters(Route route, HTTPSession session)
    {
        Pattern r = Pattern.compile(route.getPath().getPattern());

        Matcher m = r.matcher(session.getUri());

        RouteParameters parameterValues = new RouteParameters();

        if (m.matches()) {
            route.getPath().parameters.forEach((index, name) -> {
                parameterValues.put(name, m.group(index));
            });
        }

        return parameterValues;
    }
}
