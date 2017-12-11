package Yuconz.Controller;

import Framework.*;
import Framework.Container.Container;
import Framework.Routing.HTTPSession;
import Framework.Routing.Response;
import Framework.Routing.Route;
import Framework.Routing.RouteParameters;

public class AppController extends BaseController
{
    public static Response helloPositionAction(Container container, HTTPSession session, Route route)
    {
        RouteParameters parameters = route.getParameters(session);
        String name = parameters.get("name");
        String job = parameters.get("job");

        return new Response("Hello, " + name + " you have the position of: " + job);
    }
}
