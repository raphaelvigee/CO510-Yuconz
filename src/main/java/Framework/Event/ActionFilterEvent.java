package Framework.Event;

import Framework.EventDispatcher.EventInterface;
import Framework.Router.ActionInvokerInterface;
import Framework.Router.Route;
import org.eclipse.jetty.server.Request;

public class ActionFilterEvent implements EventInterface
{
    private Request request;

    private Route route;

    private Object[] parameters;

    private ActionInvokerInterface actionInvoker;

    public ActionFilterEvent(Request request, Route route, Object[] parameters, ActionInvokerInterface actionInvoker)
    {

        this.request = request;
        this.route = route;
        this.parameters = parameters;
        this.actionInvoker = actionInvoker;
    }

    public Request getRequest()
    {
        return request;
    }

    public void setRequest(Request request)
    {
        this.request = request;
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
