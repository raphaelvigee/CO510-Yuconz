package Framework.Server;

import Framework.Container.Container;
import Framework.Container.ContainerAwareInterface;
import Framework.EventDispatcher.EventDispatcher;
import Framework.EventDispatcher.EventType;
import Framework.KernelEvents;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;


public class FrameworkServer extends Server implements ContainerAwareInterface
{
    private static final Logger LOG = Log.getLogger(FrameworkServer.class);

    private Container container;

    public FrameworkServer(Container container)
    {
        super(4367);

        this.container = container;

        // Specify the Session ID Manager
        SessionIdManager sessionIdManager = new DefaultSessionIdManager(this);
        this.setSessionIdManager(sessionIdManager);

        // Sessions are bound to a context.
        ContextHandler context = new ContextHandler("/");
        this.setHandler(context);

        // Create the SessionHandler (wrapper) to handle the sessions
        SessionHandler sessions = new SessionHandler();
        context.setHandler(sessions);

        sessions.setHandler(new FrameworkHandler(getContainer()));
    }

    @Override
    protected void doStart() throws Exception
    {
        super.doStart();

        getContainer().get(EventDispatcher.class).register(KernelEvents.PRE_SEND_RESPONSE, (eventType, responseEvent) -> {
            Request request = responseEvent.getRuntimeBag().getRequest();

            LOG.info(request.getMethod() + " \"" + request.getPathInfo() + "\"");
        });
    }

    public Container getContainer()
    {
        return container;
    }

    public String getRootURL()
    {
        ServerConnector connector = (ServerConnector) getConnectors()[0];

        String hostname = connector.getName();

        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ignored) {
        }

        if (hostname.isEmpty()) {
            hostname = "localhost";
        }

        return "http://" + hostname + ":" + connector.getLocalPort();
    }
}

