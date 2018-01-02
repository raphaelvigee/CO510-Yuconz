package Yuconz.Controller;

import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;

@Route(path = "/prefix")
public class PrefixedController extends BaseController
{
    @Route(path = "/hello")
    public String helloWorldAction()
    {
        return "Hello, world ! I'm prefixed";
    }
}
