package Framework.Server.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.Response;
import Framework.Server.HTTPSession;

public class ResponseEvent implements EventInterface
{
    public HTTPSession session;

    public Response response;

    public ResponseEvent(HTTPSession session, Response response)
    {
        this.session = session;
        this.response = response;
    }
}
