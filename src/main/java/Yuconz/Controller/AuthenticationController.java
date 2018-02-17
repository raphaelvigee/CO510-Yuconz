package Yuconz.Controller;

import Yuconz.Entity.User;
import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Router.Response;
import com.sallyf.sallyf.Server.Method;
import org.eclipse.jetty.server.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            List<String> errors = new ArrayList<>();
            if (email.isEmpty()) {
                errors.add("You must enter your email address.");
            }
            if (password.isEmpty()) {
                errors.add("You must enter your password.");
            }
            if (role.isEmpty()) {
                errors.add("You must select your role.");
            }

            return new JTwigResponse("views/login.twig", new HashMap<String, Object>() {{
                put("loginErrors", errors.toArray());
                put("emailAddress", email);
            }});
        }

        User user = (User) authenticationManager.authenticate(request, email, password, role);

        if (user == null) {
            return new JTwigResponse("views/login.twig", new HashMap<String, Object>() {{
                put("loginErrors", new String[]{"Could not find user."});
                put("emailAddress", email);
            }});
        }

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
