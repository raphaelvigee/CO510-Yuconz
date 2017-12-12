package Yuconz;

import Framework.Container.Container;
import Framework.Exception.FrameworkException;
import Framework.Kernel;
import Framework.Routing.Routing;
import Yuconz.Controller.AppController;

public class Main
{
    public static void main(String[] args) throws FrameworkException
    {
        Kernel app = Kernel.newInstance();

        Container c = app.getContainer();
        Routing routing = c.get(Routing.class);

        routing.addController(AppController.class);

        app.start();
    }
}
