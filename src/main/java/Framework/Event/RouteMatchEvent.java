package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Server.RuntimeBag;

public class RouteMatchEvent implements EventInterface
{
    private RuntimeBag runtimeBag;

    public RouteMatchEvent(RuntimeBag runtimeBag)
    {
        this.runtimeBag = runtimeBag;
    }

    public RuntimeBag getRuntimeBag()
    {
        return runtimeBag;
    }

    public void setRuntimeBag(RuntimeBag runtimeBag)
    {
        this.runtimeBag = runtimeBag;
    }
}
