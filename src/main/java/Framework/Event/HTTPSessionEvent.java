package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Server.HTTPSession;

/**
 * Created by raphael on 13/12/17.
 */
public class HTTPSessionEvent implements EventInterface
{
    public HTTPSession session;

    public HTTPSessionEvent(HTTPSession session) {

        this.session = session;
    }
}
