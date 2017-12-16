package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.RouteParameters;
import Framework.Server.Request;

public class RouteParametersEvent implements EventInterface
{
    public Request request;

    public RouteParameters parameterValues;

    public RouteParametersEvent(Request request, RouteParameters parameterValues) {

        this.request = request;
        this.parameterValues = parameterValues;
    }
}
