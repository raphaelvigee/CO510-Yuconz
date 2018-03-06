package Yuconz;

import Yuconz.Controller.*;
import Yuconz.FormRenderer.CustomChoiceRenderer;
import Yuconz.FormRenderer.DateRenderer;
import Yuconz.JTwigFunction.*;
import Yuconz.Manager.AuthorisationManager;
import Yuconz.Manager.LogManager;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.ParameterResolver.UserResolver;
import Yuconz.Service.Hibernate;
import Yuconz.Voter.AuthorisationVoter;
import Yuconz.Voter.PersonalDetailsVoter;
import com.sallyf.sallyf.Container.*;
import com.sallyf.sallyf.Exception.FrameworkException;
import com.sallyf.sallyf.Form.FormManager;
import com.sallyf.sallyf.Form.Renderer.ChoiceRenderer;
import com.sallyf.sallyf.Kernel;
import com.sallyf.sallyf.Router.Router;
import com.sallyf.sallyf.Server.Configuration;
import com.sallyf.sallyf.Server.FrameworkServer;

/**
 * Yuconz HR System main entry point.
 * Initialise services, managers, resolvers, voters and
 * starts application. Will run on HTTP port 1234 by default.
 * <p>
 * Port must be changed by hardcoded value, no config file
 * necessary.
 */
public class App
{
    /**
     * Main entry point method. Will start application.
     *
     * @param args ignored, for now.
     * @throws FrameworkException
     */
    public static void main(String[] args) throws FrameworkException
    {
        int port = 1234; // Default application port

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        start(port);
    }

    /**
     * Initialise system components, start application.
     *
     * @param port
     * @return
     */
    public static Kernel start(int port)
    {
        Kernel app = Kernel.newInstance();
        Container container = app.getContainer();

        // Managers
        container.add(new ServiceDefinition<>(YuconzAuthenticationManager.class));
        container.add(new ServiceDefinition<>(Hibernate.class));
        container.add(new ServiceDefinition<>(LogManager.class));
        container.add(new ServiceDefinition<>(FormManager.class));

        container.add(new ServiceDefinition<>(AuthorisationManager.class))
                .addMethodCallDefinitions(new MethodCallDefinition(
                        "setAuthenticationManager",
                        new ServiceReference<>(YuconzAuthenticationManager.class)
                ));

        // Resolvers
        container.add(new ServiceDefinition<>(UserResolver.class));

        container.add(new ServiceDefinition<>(CurrentUserFunction.class)).addTag("jtwig.function");
        container.add(new ServiceDefinition<>(CurrentRoleFunction.class)).addTag("jtwig.function");
        container.add(new ServiceDefinition<>(FormRenderFunction.class)).addTag("jtwig.function");
        container.add(new ServiceDefinition<>(ActivePageFunction.class)).addTag("jtwig.function");
        container.add(new ServiceDefinition<>(IsGrantedFunction.class)).addTag("jtwig.function");
        container.add(new ServiceDefinition<>(RangeFunction.class)).addTag("jtwig.function");
        container.add(new ServiceDefinition<>(ServiceFunction.class)).addTag("jtwig.function");
        container.add(new ServiceDefinition<>(LocalDateFunction.class)).addTag("jtwig.function");

        // Voters
        container.add(new ServiceDefinition<>(PersonalDetailsVoter.class)).addTag("authentication.voter");
        container.add(new ServiceDefinition<>(AuthorisationVoter.class)).addTag("authentication.voter");

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
        router.registerController(PersonalDetailsController.class);

        FormManager formManager = container.get(FormManager.class);

        formManager.getRenderers().removeIf(r -> r.getClass().equals(ChoiceRenderer.class));
        formManager.addRenderer(CustomChoiceRenderer.class);
        formManager.addRenderer(DateRenderer.class);

        app.start();

        return app;
    }
}