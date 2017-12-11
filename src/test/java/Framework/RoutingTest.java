package Framework;

import Framework.Exception.RouteDuplicateException;
import Framework.Routing.*;
import fi.iki.elonen.NanoHTTPD;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RoutingTest
{
    @Test
    public void testRegexComputation()
    {
        Route route = new Route(Method.GET, "/hello/{foo}/{bar}/{dat_test}", (c, h, r) -> null);

        assertEquals("^/hello/([^/]*)/([^/]*)/([^/]*)$", route.getPath().pattern);

        HTTPSession session = new HTTPSession();
        session.setMethod(NanoHTTPD.Method.GET);
        session.setUri("/hello/YOLO/hé/dat_var");

        RouteParameters expectedParameters = new RouteParameters();
        expectedParameters.put("foo", "YOLO");
        expectedParameters.put("bar", "hé");
        expectedParameters.put("dat_test", "dat_var");

        assertEquals(expectedParameters, route.getParameters(session));
    }

    @Test
    public void testRouteMatcher() throws Exception
    {
        Route route1 = new Route(Method.GET, "/hello/{foo}/{bar}/{dat_test}", (c, h, r) -> null);
        Route route2 = new Route(Method.GET, "/qwertyuiop", (c, h, r) -> null);
        Route route3 = new Route(Method.POST, "/qwertyuiop", (c, h, r) -> null);
        Route route4 = new Route(Method.GET, "/", (c, h, r) -> null);

        Routing routing = new Routing();
        routing.addRoute(route1);
        routing.addRoute(route2);
        routing.addRoute(route3);
        routing.addRoute(route4);

        HTTPSession session1 = new HTTPSession();
        session1.setMethod(NanoHTTPD.Method.POST);
        session1.setUri("/qwertyuiop");

        Route match1 = routing.match(session1);

        assertEquals(route3, match1);

        HTTPSession session2 = new HTTPSession();
        session2.setMethod(NanoHTTPD.Method.GET);
        session2.setUri("/hello/YOLO/hé/dat_var");

        Route match2 = routing.match(session2);

        assertEquals(route1, match2);

        HTTPSession session3 = new HTTPSession();
        session3.setMethod(NanoHTTPD.Method.GET);
        session3.setUri("/nop");

        Route match3 = routing.match(session3);

        assertNull(match3);
    }

    @Test(expected = RouteDuplicateException.class)
    public void routeDuplicateExceptionTest() throws Exception
    {
        Route route1 = new Route(Method.GET, "/abc", (c, h, r) -> null);
        Route route2 = new Route(Method.GET, "/abc", (c, h, r) -> null);

        Routing routing = new Routing();
        routing.addRoute(route1);
        routing.addRoute(route2);
    }

    @Test
    public void routeDuplicateTest() throws Exception
    {
        Route route1 = new Route(Method.GET, "/abc", (c, h, r) -> null);
        Route route2 = new Route(Method.POST, "/abc", (c, h, r) -> null);

        Routing routing = new Routing();
        routing.addRoute(route1);
        routing.addRoute(route2);
    }
}
