package Framework.Router;

import Framework.BaseController;
import Framework.Container.Container;
import Framework.Container.ContainerAware;
import Framework.Event.ActionFilterEvent;
import Framework.Event.RouteParametersEvent;
import Framework.EventDispatcher.EventDispatcher;
import Framework.Exception.FrameworkException;
import Framework.Exception.RouteDuplicateException;
import Framework.Exception.UnhandledParameterException;
import Framework.KernelEvents;
import Framework.Server.Request;
import Framework.Server.Method;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router extends ContainerAware
{
    public Router(Container container)
    {
        super(container);
    }

    private ArrayList<Route> routes = new ArrayList<>();

    private ArrayList<RouteParameterResolverInterface> routeParameterResolvers = new ArrayList<>();

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

                final ActionInvokerInterface actionInvoker = (parameters) -> {
                    try {
                        return (Response) method.invoke(null, parameters);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        return null;
                    }
                };

                addAction(routeAnnotation.method(), pathPrefix + routeAnnotation.path(), (request) -> {
                    Object[] parameters = getActionParameters(parameterTypes, request);

                    ActionFilterEvent actionFilterEvent = new ActionFilterEvent(request, parameters, actionInvoker);

                    eventDispatcher.dispatch(KernelEvents.ACTION_FILTER, actionFilterEvent);

                    return actionFilterEvent.getActionInvoker().invoke(actionFilterEvent.getParameters());
                });
            }
        }
    }

    public Object[] getActionParameters(Class<?>[] parameterTypes, Request request) throws UnhandledParameterException
    {
        Object[] parameters = new Object[parameterTypes.length];
        int i = 0;
        for (Class<?> parameterType : parameterTypes) {
            Object p;

            if (parameterType == Container.class) {
                p = getContainer();
            } else if (parameterType == Request.class) {
                p = request;
            } else if (parameterType == RouteParameters.class) {
                p = getRouteParameters(request.getRoute(), request);
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

    public Route match(Request request)
    {
        for (Route route : routes) {
            if (request.getMethod().toString().equals(route.getMethod().toString())) {
                Pattern r = Pattern.compile(route.getPath().pattern);

                Matcher m = r.matcher(request.getUri());

                if (m.matches()) {
                    return route;
                }
            }
        }

        return null;
    }

    public RouteParameters getRouteParameters(Route route, Request request)
    {
        Pattern r = Pattern.compile(route.getPath().getPattern());

        Matcher m = r.matcher(request.getUri());

        RouteParameters parameterValues = new RouteParameters();

        if (m.matches()) {
            route.getPath().parameters.forEach((index, name) -> {
                parameterValues.put(name, resolveRouteParameter(m, index, name, request));
            });
        }

        EventDispatcher eventDispatcher = getContainer().get(EventDispatcher.class);

        eventDispatcher.dispatch(KernelEvents.ROUTE_PARAMETERS, new RouteParametersEvent(request, parameterValues));

        return parameterValues;
    }

    public Object resolveRouteParameter(Matcher m, Integer index, String name, Request request)
    {
        String value = m.group(index);
        Object resolvedValue = value;

        for (RouteParameterResolverInterface resolver : routeParameterResolvers) {
            if (resolver.supports(name, value, request)) {
                resolvedValue = resolver.resolve(name, value, request);
                break;
            }
        }

        return resolvedValue;
    }

    public void addRouteParameterResolver(RouteParameterResolverInterface resolver)
    {
        routeParameterResolvers.add(resolver);
    }
}
