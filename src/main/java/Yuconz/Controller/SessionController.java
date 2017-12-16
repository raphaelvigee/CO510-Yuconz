package Yuconz.Controller;

import Framework.Annotation.Route;
import Framework.BaseController;
import Framework.Container.Container;
import Framework.Router.Response;
import Framework.Router.RouteParameters;
import Framework.Server.Request;
import Framework.Session.Handler.InMemorySessionManager;
import Framework.Session.SessionInterface;

import java.util.Map;

@Route(path = "/session")
public class SessionController extends BaseController
{
    @Route(path = "/show")
    public static Response showAction(Container container, Request request)
    {
        SessionInterface session = container.get(InMemorySessionManager.class).getSession(request);

        Map elements = session.getAll();

        StringBuilder sb = new StringBuilder();

        elements.forEach((o, o2) -> {
            sb.append("<b>" + o + "</b>:" + o2.toString()+"<br>");
        });

        return new Response(sb.toString());
    }

    @Route(path = "/set/{key}/{value}")
    public static Response setAction(Container container, RouteParameters parameters, Request request)
    {
        String key = (String) parameters.get("key");
        String value = (String) parameters.get("value");

        SessionInterface session = container.get(InMemorySessionManager.class).getSession(request);

        session.set(key, value);

        return new Response("OK !");
    }
}
