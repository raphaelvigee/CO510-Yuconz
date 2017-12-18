package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.Route;
import Framework.Router.RouteParameters;
import org.eclipse.jetty.server.Request;

public class RouteParametersEvent implements EventInterface
{
    private Request request;

    private Route route;

    private RouteParameters parameterValues;

    public RouteParametersEvent(Request request, Route route, RouteParameters parameterValues)
    {
        this.request = request;
        this.route = route;
        this.parameterValues = parameterValues;
    }
}
