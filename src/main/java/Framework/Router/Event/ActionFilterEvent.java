package Framework.Router.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.ActionInvokerInterface;
import Framework.Server.HTTPSession;

public class ActionFilterEvent implements EventInterface
{
    private HTTPSession session;

    private Object[] parameters;

    private ActionInvokerInterface actionInvoker;

    public ActionFilterEvent(HTTPSession session, Object[] parameters, ActionInvokerInterface actionInvoker)
    {

        this.session = session;
        this.parameters = parameters;
        this.actionInvoker = actionInvoker;
    }

    public HTTPSession getSession()
    {
        return session;
    }

    public void setSession(HTTPSession session)
    {
        this.session = session;
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
