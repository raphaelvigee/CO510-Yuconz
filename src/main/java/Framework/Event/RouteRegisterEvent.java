package Framework.Event;

import Framework.Controller.ControllerInterface;
import Framework.EventDispatcher.EventInterface;
import Framework.Router.Route;

import java.lang.reflect.Method;

public class RouteRegisterEvent implements EventInterface
{
    private Route route;

    private Method method;

    private ControllerInterface controller;

    public RouteRegisterEvent(Route route, ControllerInterface controller, Method method)
    {
        this.route = route;
        this.method = method;
        this.controller = controller;
    }

    public Route getRoute()
    {
        return route;
    }

    public ControllerInterface getController()
    {
        return controller;
    }

    public Method getMethod()
    {
        return method;
    }
}
