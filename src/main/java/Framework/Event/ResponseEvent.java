package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.Response;
import Framework.Server.Request;

public class ResponseEvent implements EventInterface
{
    public Request request;

    public Response response;

    public ResponseEvent(Request request, Response response)
    {
        this.request = request;
        this.response = response;
    }
}
