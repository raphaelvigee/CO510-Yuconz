package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.RouteParameters;
import Framework.Server.HTTPSession;

public class RouteParametersEvent implements EventInterface
{
    public HTTPSession session;

    public RouteParameters parameterValues;

    public RouteParametersEvent(HTTPSession session, RouteParameters parameterValues) {

        this.session = session;
        this.parameterValues = parameterValues;
    }
}
