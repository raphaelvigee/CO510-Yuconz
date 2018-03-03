package Yuconz.Controller;

import Yuconz.Entity.User;
import Yuconz.Form.UserType;
import Yuconz.ParameterResolver.UserResolver;
import Yuconz.SecurityHandler.LoginRedirectHandler;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Annotation.Requirement;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.Type.FormType;
import com.sallyf.sallyf.Form.Type.SubmitType;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Router.ParameterResolver;
import com.sallyf.sallyf.Router.RouteParameters;
import com.sallyf.sallyf.Server.Method;
import org.eclipse.jetty.server.Request;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Security(value = "is_granted($, 'authenticated')", handler = LoginRedirectHandler.class)
@Route(path = "/details")
public class PersonalDetailsController extends BaseController
{
    private Object handle(Request request, Hibernate hibernate, User user, boolean create)
    {
        HashMap<String, Object> data = new HashMap<>();
        data.put("user", user.toHashMap());

        Form<FormType, FormType.FormOptions, Object> form = this.createFormBuilder(data)
                .add("user", UserType.class)
                .add("submit", SubmitType.class, options -> {
                    options.setLabel(create ? "Create" : "Update");
                })
                .getForm();

        form.handleRequest(request);

        if (form.isSubmitted() && form.isValid()) {
            user.applyHashMap((Map<String, Object>) ((Map<String, Object>) form.resolveData()).get("user"));

            Session session = hibernate.getCurrentSession();

            Transaction transaction = session.beginTransaction();
            user = (User) session.merge(user);

            session.flush();
            transaction.commit();

            User finalUser = user;
            return this.redirectToRoute("PersonalDetailsController.edit", new HashMap<String, String>()
            {{
                put("user", finalUser.getId());
            }});
        }

        return new JTwigResponse("views/personal-details/form.twig", new HashMap<String, Object>()
        {{
            put("form", form.createView());
        }});
    }

    @Route(path = "", methods = {Method.GET, Method.POST})
    @Security(value = "is_granted($, 'list_users')")
    public Object list(Request request, Hibernate hibernate)
    {
        Map<String, String[]> queryParameters = request.getParameterMap();

        int perPage = 20;
        String query = queryParameters.getOrDefault("query", new String[]{null})[0];
        int page = Integer.parseInt(queryParameters.getOrDefault("page", new String[]{"1"})[0]);

        Session session = hibernate.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query userQuery;


        if (query == null) {
            userQuery = session.createQuery("from User");
        } else {
            String[] fields = {
                    "id",
                    "firstName",
                    "lastName",
                    "email"
            };

            StringBuilder searchQuery = new StringBuilder();
            for (String field : fields) {
                if (searchQuery.length() > 0) {
                    searchQuery.append(" OR");
                }
                searchQuery.append(String.format(" lower(%s) LIKE lower(:query)", field));
            }

            userQuery = session.createQuery("from User where " + searchQuery)
                    .setParameter("query", "%" + query + "%");
        }

        userQuery
                .setFirstResult((page - 1) * perPage)
                .setMaxResults(perPage);

        List users = userQuery.list();

        long usersCount = (long) session.createQuery("select count(*) from User").uniqueResult();
        long maxPages = (long) Math.ceil(usersCount / perPage);

        transaction.commit();

        return new JTwigResponse("views/personal-details/list.twig", new HashMap<String, Object>()
        {{
            put("users", users);
            put("query", query);
            put("page", page);
            put("max_pages", maxPages);
        }});
    }

    @Route(path = "/create", methods = {Method.GET, Method.POST})
    @Security(value = "is_granted($, 'create_user')")
    public Object create(Request request, Hibernate hibernate)
    {
        User user = new User();

        return handle(request, hibernate, user, true);
    }

    @Route(path = "/{user}", methods = {Method.GET, Method.POST}, requirements = {
            @Requirement(name = "user", requirement = "([a-zA-Z]{3}[0-9]{3})")
    })
    @Security(value = "is_granted($, 'edit_user', user)")
    @ParameterResolver(name = "user", type = UserResolver.class)
    public Object edit(Request request, RouteParameters routeParameters, Hibernate hibernate)
    {
        User user = (User) routeParameters.get("user");

        return handle(request, hibernate, user, false);
    }
}
