package Yuconz;

import Framework.Authentication.AuthenticationManager;
import Framework.Authentication.DataSource.InMemoryDataSource;
import Framework.Authentication.User;
import Framework.Container.Container;
import Framework.Exception.FrameworkException;
import Framework.Kernel;
import Framework.Router.Router;
import Yuconz.Controller.AppController;
import Yuconz.Controller.AuthenticationController;
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

        AuthenticationManager authenticationManager = app.getContainer().add(AuthenticationManager.class);

        InMemoryDataSource<User> memoryDS = new InMemoryDataSource<>();
        memoryDS.addUser(new User("admin", "password"));
        memoryDS.addUser(new User("user1", "password"));
        memoryDS.addUser(new User("user2", "password"));
        authenticationManager.addDataSource(memoryDS);

        router.registerController(AppController.class);
        router.registerController(PrefixedController.class);
        router.registerController(SessionController.class);
        router.registerController(AuthenticationController.class);

        router.addRouteParameterResolver(new CapitalizerResolver());

        app.boot();
    }
}
