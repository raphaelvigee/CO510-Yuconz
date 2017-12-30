package Framework.Server;

import Framework.Container.Container;
import Framework.Container.ContainerAwareInterface;
import Framework.Event.RequestEvent;
import Framework.Event.ResponseEvent;
import Framework.Event.RouteMatchEvent;
import Framework.Event.TransformResponseEvent;
import Framework.EventDispatcher.EventDispatcher;
import Framework.KernelEvents;
import Framework.Router.Route;
import Framework.Router.Router;
import Framework.Util.Jetty;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrameworkHandler extends AbstractHandler implements ContainerAwareInterface
{
    private Container container;

    public FrameworkHandler(Container container)
    {
        this.container = container;
    }

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest servletRequest,
                       HttpServletResponse r)
    {

        org.eclipse.jetty.server.Response servletResponse = (org.eclipse.jetty.server.Response) r;

        Jetty.applyResponse(servletResponse, handle(servletRequest, servletResponse));

        baseRequest.setHandled(true);
    }

    private Framework.Router.Response handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
    {
        Request request = (Request) servletRequest;

        try {

            EventDispatcher eventDispatcher = getContainer().get(EventDispatcher.class);

            Router router = getContainer().get(Router.class);

            eventDispatcher.dispatch(KernelEvents.PRE_MATCH_ROUTE, new RequestEvent(request));

            Route route = router.match(request);

            if (route == null) {
                return new Framework.Router.Response("Not Found", Status.NOT_FOUND, "text/plain");
            }

            route = (Route) route.clone();

            RuntimeBag runtimeBag = new RuntimeBag(request, route);

            eventDispatcher.dispatch(KernelEvents.POST_MATCH_ROUTE, new RouteMatchEvent(runtimeBag));

            Object handlerResponse = route.getHandler().apply(runtimeBag);

            eventDispatcher.dispatch(KernelEvents.PRE_TRANSFORM_RESPONSE, new TransformResponseEvent(runtimeBag, handlerResponse));

            Framework.Router.Response response = router.transformResponse(runtimeBag, handlerResponse);

            eventDispatcher.dispatch(KernelEvents.PRE_SEND_RESPONSE, new ResponseEvent(runtimeBag, response));

            return response;
        } catch (Exception e) {
            e.printStackTrace();

            return new Framework.Router.Response("Internal Error", Status.INTERNAL_ERROR, "text/plain");
        }
    }

    @Override
    public Container getContainer()
    {
        return container;
    }
}
