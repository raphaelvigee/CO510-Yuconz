package Yuconz.Controller;

import Framework.Annotation.Route;
import Framework.Controller.BaseController;
import Framework.Router.RouteParameters;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Route(path = "/session")
public class SessionController extends BaseController
{
    @Route(path = "/show")
    public static String showAction(Request request)
    {
        HttpSession session = request.getSession(true);

        Enumeration<String> elementNames = session.getAttributeNames();

        StringBuilder sb = new StringBuilder();

        while (elementNames.hasMoreElements()) {
            String name = elementNames.nextElement();

            sb.append("<b>" + name + "</b>:" + session.getAttribute(name) + "<br>");
        }

        return sb.toString();
    }

    @Route(path = "/set/{key}/{value}")
    public static String setAction(RouteParameters parameters, Request request)
    {
        String key = (String) parameters.get("key");
        String value = (String) parameters.get("value");

        HttpSession session = request.getSession(true);

        session.setAttribute(key, value);

        return "OK !";
    }
}
