package Yuconz.Controller;

import Yuconz.SecurityHandler.LoginRedirectHandler;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Form.Constraint.NotEmpty;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.Type.FormType;
import com.sallyf.sallyf.Form.Type.SubmitType;
import com.sallyf.sallyf.Form.Type.TextType;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Server.Method;
import org.eclipse.jetty.server.Request;

import java.util.HashMap;

@Security(value = "is_granted($, 'authenticated')", handler = LoginRedirectHandler.class)
public class PersonalDetailsController extends BaseController
{
    @Route(path = "/details", methods = {Method.GET, Method.POST})
    public Object edit(Request request)
    {
        Form<FormType, FormType.FormOptions, Object> form = this.createFormBuilder()
                .add("firstname", TextType.class, options -> {
                    options.setLabel("First name");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("lastname", TextType.class, options -> {
                    options.setLabel("Last name");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("phone_number", TextType.class, options -> {
                    options.setLabel("Phone number");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("address_1", TextType.class, options -> {
                    options.setLabel("Address line 1");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("address_2", TextType.class, options -> {
                    options.setLabel("Address line 2");
                })
                .add("city", TextType.class, options -> {
                    options.setLabel("City");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("county", TextType.class, options -> {
                    options.setLabel("County");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("postcode", TextType.class, options -> {
                    options.setLabel("Postcode");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("country", TextType.class, options -> {
                    options.setLabel("Country");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("mobile_number", TextType.class, options -> {
                    options.setLabel("Mobile phone");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("phone_number", TextType.class, options -> {
                    options.setLabel("Phone number");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("emergency_contact", TextType.class, options -> {
                    options.setLabel("Emergency contact");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("emergency_contact_number", TextType.class, options -> {
                    options.setLabel("Emergency contact number");
                    options.getConstraints().add(new NotEmpty());
                })
                .add("submit", SubmitType.class, options -> {
                    options.setLabel("Submit");
                })
                .getForm();

        form.handleRequest(request);

        if (form.isSubmitted() && form.isValid()) {

        }

        return new JTwigResponse("views/personaldetails.twig", new HashMap<String, Object>()
        {{
            put("form", form.createView());
        }});
    }
}
