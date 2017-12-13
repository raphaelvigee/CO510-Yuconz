package Framework;

import Framework.Container.Container;
import Framework.EventDispatcher.EventDispatcher;
import Framework.Router.Route;
import Framework.Router.Router;
import Framework.Server.Server;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.ArrayList;

public class Kernel
{
    private Container container;

    Kernel(Container container)
    {
        this.container = container;
    }

    public static Kernel newInstance()
    {
        Container container = new Container();

        container.add(Server.class);
        container.add(Router.class);
        container.add(EventDispatcher.class);

        return new Kernel(container);
    }

    public Container getContainer()
    {
        return container;
    }

    public void start()
    {
        Server server = container.get(Server.class);
        Router router = container.get(Router.class);
        try {
            server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Route> routes = router.getRoutes();

        System.out.println(routes.size()+" routes registered:");
        for (Route route : routes) {
            System.out.println(route.toString());
        }
        System.out.println();

        System.out.println("Listening on http://" + server.getHostname() + ":" + server.getListeningPort());
        System.out.println();
    }
}
