package Yuconz.Controller;

import Framework.Annotation.Route;
import Framework.Controller.BaseController;

@Route(path = "/prefix")
public class PrefixedController extends BaseController
{
    @Route(path = "/hello")
    public String helloWorldAction()
    {
        return "Hello, world ! I'm prefixed";
    }
}
