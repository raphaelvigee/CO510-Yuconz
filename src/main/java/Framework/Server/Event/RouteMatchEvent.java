package Framework.Server.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Server.HTTPSession;

public class RouteMatchEvent implements EventInterface
{
    public HTTPSession session;

    public RouteMatchEvent(HTTPSession session)
    {
        this.session = session;
    }
}
