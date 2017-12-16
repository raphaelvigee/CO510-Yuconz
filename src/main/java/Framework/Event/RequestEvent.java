package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Server.Request;

public class RequestEvent implements EventInterface
{
    public Request session;

    public RequestEvent(Request session) {

        this.session = session;
    }
}
