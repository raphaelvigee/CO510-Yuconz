package Framework;

import Framework.Annotation.Route;
import Framework.Router.Response;
import Framework.Router.RouteParameters;

@Route(path = "/prefixed", name = "test_")
public class TestController extends BaseController
{
    public static Response hello1()
    {
        return new Response("hello");
    }

    public Response hello2()
    {
        return new Response("hello");
    }

    @Route(path = "/hello")
    public Response hello3()
    {
        return new Response("hello");
    }

    @Route(path = "/hello", name = "hello_named")
    public static Response hello4()
    {
        return new Response("hello");
    }

    @Route(path = "/hello/{name}")
    public static Response hello5(RouteParameters parameters)
    {
        return new Response("hello, "+parameters.get("name"));
    }
}
