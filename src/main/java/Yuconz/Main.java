package Yuconz;

import Yuconz.Controller.AppController;
import Yuconz.Controller.AuthenticationController;
import Yuconz.Controller.DashboardController;
import Yuconz.Controller.StaticController;
import Yuconz.FormRenderer.CustomChoiceRenderer;
import Yuconz.JTwigFunction.CurrentRoleFunction;
import Yuconz.JTwigFunction.CurrentUserFunction;
import Yuconz.JTwigFunction.FormRenderFunction;
import Yuconz.Manager.AuthorisationManager;
import Yuconz.Manager.LogManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Container.Container;
import com.sallyf.sallyf.Container.PlainReference;
import com.sallyf.sallyf.Container.ServiceDefinition;
import com.sallyf.sallyf.Exception.FrameworkException;
import com.sallyf.sallyf.Form.FormManager;
import com.sallyf.sallyf.Form.Renderer.ChoiceRenderer;
import com.sallyf.sallyf.Kernel;
import com.sallyf.sallyf.Router.Router;
import com.sallyf.sallyf.Server.Configuration;
import com.sallyf.sallyf.Server.FrameworkServer;

public class Main
{
    public static void main(String[] args) throws FrameworkException
    {
        start(1234);
    }

    public static Kernel start(int port)
    {
        Kernel app = Kernel.newInstance();
        Container container = app.getContainer();

        container.add(new ServiceDefinition<>(YuconzAuthenticationManager.class));
        container.add(new ServiceDefinition<>(Hibernate.class));
        container.add(new ServiceDefinition<>(LogManager.class));
        container.add(new ServiceDefinition<>(FormManager.class));
        container.add(new ServiceDefinition<>(AuthorisationManager.class));
        container.add(new ServiceDefinition<>(CurrentUserFunction.class)).addTag("jtwig.function");
        container.add(new ServiceDefinition<>(CurrentRoleFunction.class)).addTag("jtwig.function");
        container.add(new ServiceDefinition<>(FormRenderFunction.class)).addTag("jtwig.function");

        container.getServiceDefinition(FrameworkServer.class).setConfigurationReference(new PlainReference<>(new Configuration()
        {
            @Override
            public int getPort()
            {
                return port;
            }
        }));

        app.boot();

        Router router = container.get(Router.class);

        router.registerController(AppController.class);
        router.registerController(StaticController.class);
        router.registerController(AuthenticationController.class);
        router.registerController(DashboardController.class);

        FormManager formManager = container.get(FormManager.class);

        formManager.getRenderers().removeIf(r -> r.getClass().equals(ChoiceRenderer.class));
        formManager.addRenderer(CustomChoiceRenderer.class);

        app.start();

        return app;
    }
}
