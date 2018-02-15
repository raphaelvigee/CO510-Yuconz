package Yuconz;

import Yuconz.Configuration.AuthenticationConfiguration;
import Yuconz.Controller.AppController;
import Yuconz.Controller.AuthenticationController;
import Yuconz.Controller.PrefixedController;
import Yuconz.Controller.SessionController;
import Yuconz.RouteParameterResolver.CapitalizerResolver;
import com.sallyf.sallyf.AccessDecisionManager.AccessDecisionManager;
import com.sallyf.sallyf.Authentication.AuthenticationManager;
import com.sallyf.sallyf.Container.Container;
import com.sallyf.sallyf.Container.ServiceDefinition;
import com.sallyf.sallyf.Exception.FrameworkException;
import com.sallyf.sallyf.ExpressionLanguage.ExpressionLanguage;
import com.sallyf.sallyf.FreeMarker.FreeMarker;
import com.sallyf.sallyf.Kernel;
import com.sallyf.sallyf.Router.Router;

public class Main
{
    public static void main(String[] args) throws FrameworkException
    {
        Kernel app = Kernel.newInstance();
        Container container = app.getContainer();

        container.add(new ServiceDefinition<>(AuthenticationManager.class, new AuthenticationConfiguration()));
        container.add(new ServiceDefinition<>(ExpressionLanguage.class));
        container.add(new ServiceDefinition<>(AccessDecisionManager.class));
        container.add(new ServiceDefinition<>(FreeMarker.class));

        app.boot();

        Router router = container.get(Router.class);

        router.registerController(AppController.class);
        router.registerController(PrefixedController.class);
        router.registerController(SessionController.class);
        router.registerController(AuthenticationController.class);

        router.addRouteParameterResolver(new CapitalizerResolver());

        app.start();
    }
}
