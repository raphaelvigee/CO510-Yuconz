package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Server.Request;

public class RouteMatchEvent implements EventInterface
{
    public Request request;

    public RouteMatchEvent(Request request)
    {
        this.request = request;
    }
}
