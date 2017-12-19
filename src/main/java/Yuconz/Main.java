package Yuconz;

import Framework.Container.Container;
import Framework.Exception.FrameworkException;
import Framework.Kernel;
import Framework.Router.Router;
import Yuconz.Controller.AppController;
import Yuconz.Controller.PrefixedController;
import Yuconz.Controller.SessionController;
import Yuconz.RouteParameterResolver.CapitalizerResolver;

public class Main
{
    public static void main(String[] args) throws FrameworkException
    {
        Kernel app = Kernel.newInstance();

        Container c = app.getContainer();
        Router router = c.get(Router.class);

        router.addController(AppController.class);
        router.addController(PrefixedController.class);
        router.addController(SessionController.class);

        router.addRouteParameterResolver(new CapitalizerResolver());

        app.start();
    }
}
