package Framework.Server;

import Framework.Annotation.Route;
import Framework.Controller.BaseController;
import Framework.Router.Response;

public class TestController extends BaseController
{
    @Route(path = "/test1")
    public Response test1() {
        Response r = new Response("OK");

        r.addHeader("test1", "hello1");

        return r;
    }
}
