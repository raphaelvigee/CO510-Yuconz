package Yuconz.Controller;

import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.FreeMarker.FreeMarkerResponse;
import com.sallyf.sallyf.Router.Response;
import com.sallyf.sallyf.Router.RouteParameters;

import java.util.HashMap;
import java.util.Map;

public class AppController extends BaseController
{
    @Route(path = "/hello")
    public FreeMarkerResponse helloWorldAction()
    {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "world");

        return new FreeMarkerResponse("/home/raphael/Documents/Code/Yuconz/resources/views/helloworld.ftl", data);
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
