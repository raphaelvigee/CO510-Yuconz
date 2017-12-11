package Yuconz;

import Framework.Container;
import Framework.Exception.FrameworkException;
import Framework.Kernel;
import Framework.Routing;
import Yuconz.Controller.App2Controller;
import Yuconz.Controller.AppController;

public class Main
{
    public static void main(String[] args) throws FrameworkException
    {
        Kernel app = Kernel.newInstance();

        Container c = app.getContainer();
        Routing routing = c.get(Routing.class);

        routing.get("/hello/{name}/{job}", AppController::helloPositionAction);

        routing.addController(App2Controller.class);

        app.start();
    }
}
