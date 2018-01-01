package Framework.Authentication;

import Framework.Annotation.Route;
import Framework.Controller.BaseController;
import org.eclipse.jetty.server.Request;

public class TestController extends BaseController
{
    @Route(path = "/authenticate")
    public String authenticate(Request request, AuthenticationManager authenticationManager) throws AuthenticationException
    {
        authenticationManager.authenticate(request, "admin", "password");

        return "OK";
    }

    @Route(path = "/user")
    public String user(User user)
    {
        return user.getUsername();
    }
}