package Yuconz.Controller;

import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Router.Response;
import com.sallyf.sallyf.Router.RouteParameters;

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
