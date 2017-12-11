package Framework;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;

public class YServer extends NanoHTTPD implements ContainerAwareInterface
{
    private Container container;

    public YServer()
    {
        super(4367);

        try {
            start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Listening on http://" + getHostname() + ":" + getListeningPort());
    }

    @Override
    public Response serve(IHTTPSession s)
    {
        Framework.HTTPSession session = Framework.HTTPSession.create(s);

        Routing routing = container.get(Routing.class);

        Route match = routing.match(session);

        if (match == null) {
            return newFixedLengthResponse("404");
        }

        Framework.Response response = match.getHandler().apply(container, session, match);

        return newFixedLengthResponse(response.status, response.mimeType, response.content);
    }

    @Override
    public String getHostname()
    {
        return super.getHostname() == null ? "localhost" : super.getHostname();
    }

    @Override
    public void setContainer(Container container)
    {
        this.container = container;
    }
}

