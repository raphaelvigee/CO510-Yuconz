package Yuconz.Controller;

import Framework.Annotation.Route;
import Framework.BaseController;
import Framework.Container.Container;
import Framework.Server.HTTPSession;
import Framework.Router.Response;
import Framework.Router.RouteParameters;

public class AppController extends BaseController
{
    @Route(path = "/hello")
    public static Response helloWorldAction()
    {
        return new Response("Hello, world");
    }

    @Route(path = "/hello/{name}/{job}")
    public static Response helloPositionAction(Container container, HTTPSession session, Framework.Router.Route route)
    {
        RouteParameters parameters = route.getParameters(session);
        String name = parameters.get("name");
        String job = parameters.get("job");

        return new Response("Hello, " + name + " you have the position of: " + job);
    }
}
