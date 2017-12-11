package Yuconz.Controller;

import Framework.*;

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
