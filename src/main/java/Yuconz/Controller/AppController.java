package Yuconz.Controller;

import Framework.Annotation.Route;
import Framework.Controller.BaseController;
import Framework.Router.Response;
import Framework.Router.RouteParameters;

public class AppController extends BaseController
{
    @Route(path = "/hello")
    public String helloWorldAction()
    {
        return "Hello, world";
    }

    @Route(path = "/hello/{name}/{job}")
    public String helloPositionAction(RouteParameters parameters)
    {
        String name = (String) parameters.get("name");
        String job = (String) parameters.get("job");

        return "Hello, " + name + " you have the position of: " + job;
    }

    @Route(path = "/redirect")
    public Response redirectAction()
    {
        return redirect("https://google.com");
    }
}
