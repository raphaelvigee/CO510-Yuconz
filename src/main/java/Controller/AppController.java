package Controller;

import Framework.ActionInterface;
import Framework.BaseController;
import Framework.HTTPSession;
import Framework.Response;

public class AppController extends BaseController
{
    public ActionInterface helloWorldAction = (HTTPSession session) -> {
        return new Response("Hello, " + session.getParms().get("name"));
    };
}
