package Framework.Router.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.ActionInvokerInterface;
import Framework.Server.HTTPSession;

public class PreInvokeActionEvent implements EventInterface
{
    private HTTPSession session;

    private Object[] parameters;

    private ActionInvokerInterface actionInvoker;

    public PreInvokeActionEvent(HTTPSession session, Object[] parameters, ActionInvokerInterface actionInvoker)
    {

        this.session = session;
        this.parameters = parameters;
        this.actionInvoker = actionInvoker;
    }

    public ActionInvokerInterface getActionInvoker()
    {
        return actionInvoker;
    }
}
