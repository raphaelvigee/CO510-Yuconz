package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Server.RuntimeBag;

public class TransformResponseEvent implements EventInterface
{
    private RuntimeBag runtimeBag;

    private Object handlerResponse;

    public TransformResponseEvent(RuntimeBag runtimeBag, Object handlerResponse)
    {
        this.runtimeBag = runtimeBag;
        this.handlerResponse = handlerResponse;
    }

    public RuntimeBag getRuntimeBag()
    {
        return runtimeBag;
    }

    public void setRuntimeBag(RuntimeBag runtimeBag)
    {
        this.runtimeBag = runtimeBag;
    }

    public Object getHandlerResponse()
    {
        return handlerResponse;
    }

    public void setHandlerResponse(Object handlerResponse)
    {
        this.handlerResponse = handlerResponse;
    }
}
