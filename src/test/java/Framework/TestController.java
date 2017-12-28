package Framework;

import Framework.Annotation.Route;
import Framework.Controller.BaseController;
import Framework.Router.Response;
import Framework.Router.RouteParameters;

@Route(path = "/prefixed", name = "test_")
public class TestController extends BaseController
{
    public Response hello1()
    {
        return new Response("hello");
    }

    @Route(path = "/hello", name = "hello_named")
    public Response hello4()
    {
        return new Response("hello");
    }

    @Route(path = "/hello/{name}")
    public Response hello5(RouteParameters parameters)
    {
        return new Response("hello, " + parameters.get("name"));
    }

    @Route(path = "/transform/{name}")
    public Object toTransform(RouteParameters parameters)
    {
        return "hello, " + parameters.get("name");
    }
}
