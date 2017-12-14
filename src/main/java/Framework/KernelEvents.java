package Framework;

import Framework.Event.ActionFilterEvent;
import Framework.Event.HTTPSessionEvent;
import Framework.Event.ResponseEvent;
import Framework.Event.RouteMatchEvent;
import Framework.EventDispatcher.EventType;

public class KernelEvents
{
    public static final EventType<HTTPSessionEvent> PRE_MATCH_ROUTE = new EventType<>("kernel.pre_match_route");

    public static final EventType<RouteMatchEvent> POST_MATCH_ROUTE = new EventType<>("kernel.post_match_route");

    public static final EventType<ResponseEvent> PRE_SEND_RESPONSE = new EventType<>("kernel.pre_send_response");

    public static final EventType<ActionFilterEvent> ACTION_FILTER = new EventType<>("kernel.pre_invoke_action");
}
