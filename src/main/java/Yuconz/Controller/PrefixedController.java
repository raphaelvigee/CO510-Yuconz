package Yuconz.Controller;

import Framework.Annotation.Route;
import Framework.BaseController;
import Framework.Container.Container;
import Framework.Routing.HTTPSession;
import Framework.Routing.Response;
import Framework.Routing.RouteParameters;

@Route(path = "/prefix")
public class PrefixedController extends BaseController
{
    @Route(path = "/hello")
    public static Response helloWorldAction()
    {
        return new Response("Hello, world ! I'm prefixed");
    }
}
