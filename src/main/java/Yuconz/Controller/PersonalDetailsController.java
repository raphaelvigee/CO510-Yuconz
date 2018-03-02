package Yuconz.Controller;

import Yuconz.Entity.User;
import Yuconz.Form.UserType;
import Yuconz.SecurityHandler.LoginRedirectHandler;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.Type.FormType;
import com.sallyf.sallyf.Form.Type.SubmitType;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Router.RouteParameters;
import com.sallyf.sallyf.Server.Method;
import org.eclipse.jetty.server.Request;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.Map;

@Security(value = "is_granted($, 'authenticated')", handler = LoginRedirectHandler.class)
@Route(path = "/details/{user}")
public class PersonalDetailsController extends BaseController
{
    @Route(path = "", methods = {Method.GET, Method.POST})
    public Object edit(Request request, RouteParameters routeParameters, Hibernate hibernate)
    {
        Session session = hibernate.getCurrentSession();

        User user = session.find(User.class, routeParameters.get("user"));

        HashMap<String, Object> data = new HashMap<>();
        data.put("user", user.toHashMap());

        Form<FormType, FormType.FormOptions, Object> form = this.createFormBuilder(data)
                .add("user", UserType.class)
                .add("submit", SubmitType.class, options -> {
                    options.setLabel("Update");
                })
                .getForm();

        form.handleRequest(request);

        if (form.isSubmitted() && form.isValid()) {
            user.applyHashMap((Map<String, Object>) ((Map<String, Object>) form.resolveData()).get("user"));

            Transaction transaction = session.beginTransaction();
            session.persist(user);

            session.flush();
            transaction.commit();

            return this.redirectToRoute("PersonalDetailsController.edit", new HashMap<String, String>()
            {{
                put("user", user.getId());
            }});
        }

        return new JTwigResponse("views/personaldetails.twig", new HashMap<String, Object>()
        {{
            put("form", form.createView());
        }});
    }
}
