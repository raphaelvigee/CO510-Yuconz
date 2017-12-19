package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.ActionInvokerInterface;
import Framework.Server.RuntimeBag;

public class ActionFilterEvent implements EventInterface
{
    private Object[] parameters;

    private ActionInvokerInterface actionInvoker;

    private RuntimeBag runtimeBag;

    public ActionFilterEvent(RuntimeBag runtimeBag, Object[] parameters, ActionInvokerInterface actionInvoker)
    {
        this.runtimeBag = runtimeBag;
        this.parameters = parameters;
        this.actionInvoker = actionInvoker;
    }

    public RuntimeBag getRuntimeBag()
    {
        return runtimeBag;
    }

    public void setRuntimeBag(RuntimeBag runtimeBag)
    {
        this.runtimeBag = runtimeBag;
    }

    public Object[] getParameters()
    {
        return parameters;
    }

    public void setParameters(Object[] parameters)
    {
        this.parameters = parameters;
    }

    public ActionInvokerInterface getActionInvoker()
    {
        return actionInvoker;
    }

    public void setActionInvoker(ActionInvokerInterface actionInvoker)
    {
        this.actionInvoker = actionInvoker;
    }
}
