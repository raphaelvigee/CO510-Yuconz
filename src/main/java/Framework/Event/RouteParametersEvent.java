package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.RouteParameters;
import Framework.Server.RuntimeBag;

public class RouteParametersEvent implements EventInterface
{
    private RouteParameters parameterValues;

    private RuntimeBag runtimeBag;

    public RouteParametersEvent(RuntimeBag runtimeBag, RouteParameters parameterValues)
    {
        this.runtimeBag = runtimeBag;
        this.parameterValues = parameterValues;
    }
}
