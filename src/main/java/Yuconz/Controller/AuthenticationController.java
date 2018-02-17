package Yuconz.Controller;

import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Router.Response;
import com.sallyf.sallyf.Server.Method;
import org.eclipse.jetty.server.Request;

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
    public JTwigResponse login()
    {
        return new JTwigResponse("views/login.twig");
    }

    @Route(path = "/logout")
    public Response logout(Request request, YuconzAuthenticationManager authenticationManager)
    {
        authenticationManager.logout(request);

        return this.redirectToRoute("AppController.index");
    }
}
