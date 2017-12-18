//package Framework.Server;
//
//import Framework.Container.Container;
//import Framework.Container.ContainerAwareInterface;
//import Framework.Event.RequestEvent;
//import Framework.Event.ResponseEvent;
//import Framework.Event.RouteMatchEvent;
//import Framework.EventDispatcher.EventDispatcher;
//import Framework.KernelEvents;
//import Framework.Router.Route;
//import Framework.Router.Router;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//public class FrameworkServlet extends HttpServlet implements ContainerAwareInterface
//{
//    private Container container;
//
//    public FrameworkServlet(Container container)
//    {
//        this.container = container;
//    }
//
//    @Override
//    public void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException
//    {
//        Framework.Server.Request request = (Framework.Server.Request) servletRequest;
//        JettyResponse jettyResponse = (JettyResponse) servletResponse;
//
//        jettyResponse.applyResponse(handle(servletRequest, servletResponse));
//
//        request.setHandled(true);
//    }
//
//    private Framework.Router.Response handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
//    {
//        Framework.Server.Request request = (Framework.Server.Request) servletRequest;
//
//        try {
//
//            EventDispatcher eventDispatcher = getContainer().get(EventDispatcher.class);
//
//            Router router = getContainer().get(Router.class);
//
//            eventDispatcher.dispatch(KernelEvents.PRE_MATCH_ROUTE, new RequestEvent(request));
//
//            Route route = router.match(request);
//            request.setRoute(route);
//
//            eventDispatcher.dispatch(KernelEvents.POST_MATCH_ROUTE, new RouteMatchEvent(request));
//
//            if (route == null) {
//                return new Framework.Router.Response("Not Found", Status.NOT_FOUND, "text/plain");
//            }
//
//            Framework.Router.Response response = route.getHandler().apply(request);
//
//            eventDispatcher.dispatch(KernelEvents.PRE_SEND_RESPONSE, new ResponseEvent(request, response));
//
//            return response;
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            return new Framework.Router.Response("Internal Error", Status.INTERNAL_ERROR, "text/plain");
//        }
//    }
//
//    @Override
//    public Container getContainer()
//    {
//        return container;
//    }
//}