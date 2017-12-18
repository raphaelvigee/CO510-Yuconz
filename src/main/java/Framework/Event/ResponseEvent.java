package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.Response;
import org.eclipse.jetty.server.Request;

public class ResponseEvent implements EventInterface
{
    private Request request;

    private Response response;

    public ResponseEvent(Request request, Response response)
    {
        this.request = request;
        this.response = response;
    }
}
