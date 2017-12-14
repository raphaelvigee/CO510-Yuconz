package Yuconz.Controller;

import Framework.Annotation.Route;
import Framework.BaseController;
import Framework.Container.Container;
import Framework.Router.Response;
import Framework.Router.RouteParameters;
import Framework.Server.HTTPSession;

public class AppController extends BaseController
{
    @Route(path = "/hello")
    public static Response helloWorldAction(Container container)
    {
        return new Response("Hello, world");
    }

    @Route(path = "/hello/{name}/{job}")
    public static Response helloPositionAction(RouteParameters parameters)
    {
        String name = (String) parameters.get("name");
        String job = (String) parameters.get("job");

        return new Response("Hello, " + name + " you have the position of: " + job);
    }
}
