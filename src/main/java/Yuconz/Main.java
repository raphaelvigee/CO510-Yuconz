package Yuconz;

import Yuconz.Controller.AppController;
import Yuconz.Controller.AuthenticationController;
import Yuconz.Controller.PrefixedController;
import Yuconz.Controller.SessionController;
import Yuconz.RouteParameterResolver.CapitalizerResolver;
import com.sallyf.sallyf.Authentication.AuthenticationManager;
import com.sallyf.sallyf.Authentication.DataSource.InMemoryDataSource;
import com.sallyf.sallyf.Authentication.User;
import com.sallyf.sallyf.Container.Container;
import com.sallyf.sallyf.Exception.FrameworkException;
import com.sallyf.sallyf.Kernel;
import com.sallyf.sallyf.Router.Router;

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
