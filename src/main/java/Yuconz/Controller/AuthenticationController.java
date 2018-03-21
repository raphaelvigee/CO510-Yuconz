package Yuconz.Controller;

import Yuconz.Entity.User;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.LoginRole;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Form.Constraint.NotEmpty;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.Type.*;
import com.sallyf.sallyf.Form.ValidationError;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Router.Response;
import com.sallyf.sallyf.Server.Method;
import org.eclipse.jetty.server.Request;

import java.util.*;

/**
 * Controller for authentication routes.
 */
@Route(path = "/auth")
public class AuthenticationController extends BaseController
{
    /**
     * Login route, displays login form to user.
     *
     * @param request               the request
     * @param authenticationManager the system's authentication manager
     * @return response
     */
    @Route(path = "/login", methods = {Method.GET, Method.POST})
    public Object login(Request request, YuconzAuthenticationManager authenticationManager)
    {
        User currentUser = (User) authenticationManager.getUser();

        Map<String, Object> in = new LinkedHashMap<>();

        if (currentUser != null) {
            in.put("email", currentUser.getEmail());
        }

        String roleStr = request.getParameter("role");
        if (roleStr != null && !roleStr.isEmpty()) {
            try {
                LoginRole role = LoginRole.valueOf(roleStr);
                in.put("role", role);
            } catch (IllegalArgumentException ignore) {
            }
        }

        in.put("next", request.getParameter("next"));

        Form<FormType, FormType.FormOptions, Object> form = this.createFormBuilder(in)
                .add("email", TextType.class, options -> {
                    options.setLabel("Email Address");
                    options.getAttributes().put("placeholder", "example@yuconz.co.uk");
                    options.getAttributes().put("autofocus", "autofocus");

                    options.getConstraints().add(new NotEmpty());
                })
                .add("password", PasswordType.class, options -> {
                    options.setLabel("Password");
                    options.getAttributes().put("placeholder", "123");

                    options.getConstraints().add(new NotEmpty());
                })
                .add("role", ChoiceType.class, options -> {
                    options.setLabel("Role");

                    options.setChoices(new LinkedHashSet<>(Arrays.asList(LoginRole.values())));
                    options.setMultiple(false);
                    options.setExpanded(false);

                    options.setChoiceLabelResolver(c -> ((LoginRole) c).getName());
                })
                .add("next", HiddenType.class)
                .add("submit", SubmitType.class, options -> {
                    options.setLabel("Login");
                })
                .getForm();

        form.handleRequest();

        if (form.isSubmitted() && form.isValid()) {
            Map<String, Object> data = (Map<String, Object>) form.resolveData();

            String email = String.valueOf(data.get("email"));
            String password = String.valueOf(data.get("password"));
            String role = String.valueOf(data.get("role"));
            String next = String.valueOf(data.get("next"));

            User user = (User) authenticationManager.authenticate(request, email, password, role);

            if (user != null) {
                if (!next.isEmpty()) {
                    return this.redirect(next);
                }

                return this.redirectToRoute("AppController.index");
            } else {
                form.getErrorsBag().addError(new ValidationError("Invalid Email / Password or Role"));
            }
        }

        return new JTwigResponse("views/login.twig", new HashMap<String, Object>()
        {{
            put("form", form.createView());
        }});
    }

    /**
     * Logout route, redirects to app index.
     *
     * @param request               the request
     * @param authenticationManager the system's authentication manager
     * @return response (redirection)
     */
    @Route(path = "/logout")
    public Response logout(Request request, YuconzAuthenticationManager authenticationManager)
    {
        authenticationManager.logout(request);

        return this.redirectToRoute("AppController.index");
    }
}
