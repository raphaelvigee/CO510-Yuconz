package Yuconz;

import Yuconz.Controller.AppController;
import Yuconz.Controller.AuthenticationController;
import Yuconz.Controller.DashboardController;
import Yuconz.Controller.StaticController;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Resolver.RuntimeBagResolver;
import com.sallyf.sallyf.AccessDecisionManager.AccessDecisionManager;
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

        container.add(new ServiceDefinition<>(YuconzAuthenticationManager.class));
        container.add(new ServiceDefinition<>(ExpressionLanguage.class));
        container.add(new ServiceDefinition<>(AccessDecisionManager.class));
        container.add(new ServiceDefinition<>(FreeMarker.class));

        app.boot();

        Router router = container.get(Router.class);

        router.registerController(AppController.class);
        router.registerController(StaticController.class);
        router.registerController(AuthenticationController.class);

        router.registerController(DashboardController.class);

        router.addActionParameterResolver(new RuntimeBagResolver());

        app.start();
    }
}
