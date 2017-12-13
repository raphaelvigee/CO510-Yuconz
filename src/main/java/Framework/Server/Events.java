package Framework.Server;

import Framework.EventDispatcher.EventType;
import Framework.Server.Event.HTTPSessionEvent;
import Framework.Server.Event.ResponseEvent;
import Framework.Server.Event.RouteMatchEvent;

public class Events
{
    public static final EventType<HTTPSessionEvent> PRE_MATCH = new EventType<>("server.pre_match");

    public static final EventType<ResponseEvent> PRE_SEND_RESPONSE = new EventType<>("server.pre_send_response");

    public static final EventType<RouteMatchEvent> POST_MATCH = new EventType<>("server.post_match");
}
