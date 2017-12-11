package Yuconz.Controller;

import Framework.*;
import Framework.Annotation.Route;
import Framework.Routing.Response;

public class App2Controller extends BaseController
{
    @Route(path = "/hello")
    public static Response helloWorldAction()
    {
        return new Response("Hello, world");
    }
}
