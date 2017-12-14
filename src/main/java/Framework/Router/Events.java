package Framework.Router;

import Framework.EventDispatcher.EventType;
import Framework.Router.Event.ActionFilterEvent;

public class Events
{
    public static final EventType<ActionFilterEvent> ACTION_FILTER = new EventType<>("router.pre_invoke_action");
}
