package Yuconz.Controller;

import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Authentication.AuthenticationException;
import com.sallyf.sallyf.Authentication.AuthenticationManager;
import com.sallyf.sallyf.Authentication.User;
import com.sallyf.sallyf.Authentication.Voter.LoggedIn;
import com.sallyf.sallyf.Controller.BaseController;
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
        return user != null ? user.getUsername() : "Anonymous";
    }

    @Route(path = "/secured")
    @Security({LoggedIn.class})
    public String secured()
    {
        return "Secured";
    }
}
