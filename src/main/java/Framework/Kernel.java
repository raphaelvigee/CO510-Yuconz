package Framework;

import Framework.Container.Container;
import Framework.EventDispatcher.EventDispatcher;
import Framework.Exception.FrameworkException;
import Framework.Router.Route;
import Framework.Router.Router;
import Framework.Router.URLGenerator;
import Framework.Server.FrameworkServer;

import java.util.HashMap;

public class Kernel
{
    private Container container;

    Kernel(Container container)
    {
        this.container = container;
    }

    public static Kernel newInstance() throws FrameworkException
    {
        Container container = new Container();

        container.add(FrameworkServer.class);
        container.add(Router.class);
        container.add(URLGenerator.class);
        container.add(EventDispatcher.class);

        return new Kernel(container);
    }

    public Container getContainer()
    {
        return container;
    }

    public void start()
    {
        FrameworkServer server = container.get(FrameworkServer.class);
        Router router = container.get(Router.class);
        try {
            server.start();
            //server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, Route> routes = router.getRoutes();

        System.out.println(routes.size() + " routes registered:");
        for (Route route : routes.values()) {
            System.out.println(route.getName() + " -> " + route.toString());
        }
        System.out.println();

        System.out.println("Listening on " + server.getRootURL());
        System.out.println();
    }

    public void stop()
    {
        FrameworkServer server = container.get(FrameworkServer.class);

        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
