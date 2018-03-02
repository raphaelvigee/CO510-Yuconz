package Yuconz.Controller;

import Yuconz.Entity.User;
import Yuconz.Manager.YuconzAuthenticationManager;
import Yuconz.Model.Role;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

@Route(path = "/auth")
public class AuthenticationController extends BaseController
{
    @Route(path = "/login", methods = {Method.GET, Method.POST})
    public Object login(Request request, YuconzAuthenticationManager authenticationManager)
    {
        Form<FormType, FormType.FormOptions, Object> form = this.createFormBuilder()
                .add("email", TextType.class, options -> {
                    options.setLabel("Email Address");

                    options.getConstraints().add(new NotEmpty());
                })
                .add("password", PasswordType.class, options -> {
                    options.setLabel("Password");

                    options.getConstraints().add(new NotEmpty());
                })
                .add("role", ChoiceType.class, options -> {
                    options.setLabel("Role");

                    options.setChoices(new LinkedHashSet<>(Arrays.asList(Role.values())));
                    options.setMultiple(false);
                    options.setExpanded(false);

                    options.setChoiceLabelResolver(c -> ((Role) c).getName());
                })
                .add("submit", SubmitType.class, options -> {
                    options.setLabel("Login");
                })
                .getForm();

        form.handleRequest(request);

        if (form.isSubmitted() && form.isValid()) {
            Map<String, Object> data = (Map<String, Object>) form.resolveData();

            String email = String.valueOf(data.get("email"));
            String password = String.valueOf(data.get("password"));
            String role = String.valueOf(data.get("role"));

            User user = (User) authenticationManager.authenticate(request, email, password, role);

            if (user != null) {
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

    @Route(path = "/logout")
    public Response logout(Request request, YuconzAuthenticationManager authenticationManager)
    {
        authenticationManager.logout(request);

        return this.redirectToRoute("AppController.index");
    }
}
