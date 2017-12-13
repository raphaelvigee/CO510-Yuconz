package Framework.Server.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.Route;
import Framework.Server.HTTPSession;

public class RouteMatchEvent implements EventInterface
{
    public HTTPSession session;

    public Route match;

    public RouteMatchEvent(HTTPSession session, Route match)
    {
        this.session = session;
        this.match = match;
    }
}
