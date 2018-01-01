package Yuconz.Controller;

import Framework.Annotation.Route;
import Framework.Authentication.AuthenticationException;
import Framework.Authentication.AuthenticationManager;
import Framework.Authentication.User;
import Framework.Controller.BaseController;
import org.eclipse.jetty.server.Request;

@Route(path = "/auth")
public class AuthenticationController extends BaseController
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
