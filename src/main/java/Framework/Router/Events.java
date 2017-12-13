package Framework.Router;

import Framework.EventDispatcher.EventType;
import Framework.Router.Event.PreInvokeActionEvent;

public class Events
{
    public static final EventType<PreInvokeActionEvent> PRE_INVOKE_ACTION = new EventType<>("router.pre_invoke_action");
}
