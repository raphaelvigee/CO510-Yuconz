package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.Response;
import Framework.Server.RuntimeBag;

public class ResponseEvent implements EventInterface
{
    private RuntimeBag runtimeBag;

    private Response response;

    public ResponseEvent(RuntimeBag runtimeBag, Response response)
    {
        this.runtimeBag = runtimeBag;
        this.response = response;
    }

    public RuntimeBag getRuntimeBag()
    {
        return runtimeBag;
    }

    public void setRuntimeBag(RuntimeBag runtimeBag)
    {
        this.runtimeBag = runtimeBag;
    }

    public Response getResponse()
    {
        return response;
    }

    public void setResponse(Response response)
    {
        this.response = response;
    }
}
