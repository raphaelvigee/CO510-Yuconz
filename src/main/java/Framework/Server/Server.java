package Framework.Server;

import Framework.Container.Container;
import Framework.Container.ContainerAwareInterface;
import Framework.Router.*;
import fi.iki.elonen.NanoHTTPD;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server extends NanoHTTPD implements ContainerAwareInterface
{
    private Container container;

    public Server()
    {
        super(4367);
    }

    @Override
    public Response serve(IHTTPSession s)
    {
        Framework.Server.HTTPSession session = Framework.Server.HTTPSession.create(s);

        Router router = container.get(Router.class);

        Route match = router.match(session);

        if (match == null) {
            return newFixedLengthResponse(Status.NOT_FOUND, "text/plain", "Not found");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        System.out.println("["+dateFormat.format(date)+"] "+session.getMethod()+" \""+session.getUri()+"\"");

        Framework.Router.Response response;

        try {
            response = match.getHandler().apply(container, session, match);
        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse(Status.INTERNAL_ERROR, "text/plain", "Internal Error");
        }

        return newFixedLengthResponse(response.getStatus(), response.getMimeType(), response.getContent());
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

