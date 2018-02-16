package Yuconz.Controller;

import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.FreeMarker.FreeMarkerResponse;
import com.sallyf.sallyf.Router.Response;
import com.sallyf.sallyf.Server.Method;
import org.eclipse.jetty.server.Request;

import java.util.HashMap;

@Route(path = "/auth")
public class AuthenticationController extends BaseController
{
    @Route(path = "/login", method = Method.POST)
    public Object loginPost(Request request, YuconzAuthenticationManager authenticationManager)
    {
        String email = String.valueOf(request.getParameter("email"));
        String password = String.valueOf(request.getParameter("password"));
        String role = String.valueOf(request.getParameter("role"));

        if (email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            return this.redirectToRoute("AuthenticationController.login");
        }

        authenticationManager.authenticate(request, email, password, role);

        return this.redirectToRoute("AppController.index");
    }

    @Route(path = "/login")
    public FreeMarkerResponse login()
    {
        return new FreeMarkerResponse("login.ftl", new HashMap<>());
    }

    @Route(path = "/logout")
    public Response logout(Request request, YuconzAuthenticationManager authenticationManager)
    {
        authenticationManager.logout(request);

        return this.redirectToRoute("AppController.index");
    }
}
