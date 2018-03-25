package Yuconz.Controller;

import Yuconz.Entity.InitialEmploymentDetailsRecord;
import Yuconz.Entity.User;
import Yuconz.Form.InitialEmploymentDetailsType;
import Yuconz.Form.UserType;
import Yuconz.App;
import Yuconz.FormUtils;
import Yuconz.Model.FlashMessage;
import Yuconz.ParameterResolver.UserResolver;
import Yuconz.SecurityHandler.LoginRedirectHandler;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Annotation.Requirement;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.FlashManager.FlashManager;
import com.sallyf.sallyf.Form.Form;
import com.sallyf.sallyf.Form.FormBuilder;
import com.sallyf.sallyf.Form.Type.FormType;
import com.sallyf.sallyf.Form.Type.SubmitType;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Router.ParameterResolver;
import com.sallyf.sallyf.Router.RouteParameters;
import com.sallyf.sallyf.Server.Method;
import com.sallyf.sallyf.Server.RuntimeBag;
import com.sallyf.sallyf.Utils.MapUtils;
import org.eclipse.jetty.server.Request;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sallyf.sallyf.Utils.MapUtils.entry;

/**
 * Controller for personal details views.
 */
@Security(value = "is_granted('authenticated')", handler = LoginRedirectHandler.class)
@Route(path = "/employee")
public class EmployeesController extends BaseController
{
    /**
     * Handles the editing and creation of personal details.
     *
     * @param user   user to edit
     * @param create is new user
     * @return response
     */
    private Object handle(User user, boolean create)
    {
        Hibernate hibernate = this.getContainer().get(Hibernate.class);
        FlashManager flashManager = this.getContainer().get(FlashManager.class);

        FormBuilder<FormType, FormType.FormOptions, Object> builder = createFormBuilder()
                .add("user", UserType.class, options -> {
                    options.setCreate(create);
                });

        if (create) {
            builder.add("ied", InitialEmploymentDetailsType.class);
        }

        builder.add("submit", SubmitType.class, options -> {
            options.setLabel(create ? "Create" : "Update");
        });

        Form<FormType, FormType.FormOptions, Object> form = builder.getForm();

        HashMap<String, Object> in = new HashMap<>();
        in.put("user", FormUtils.normalize(form.getChild("user"), user));

        InitialEmploymentDetailsRecord ied = new InitialEmploymentDetailsRecord();

        if (create) {
            in.put("ied", FormUtils.normalize(form.getChild("ied"), ied));
        }

        form.setData(in);
        form.propagateChildData();

        form.handleRequest();

        if (form.isSubmitted() && form.isValid()) {
            Map<String, Object> data = (Map<String, Object>) form.resolveData();
            Map<String, Object> userData = (Map<String, Object>) data.get("user");
            user.applyHashMap(userData);

            String password = (String) userData.get("password");
            if (!password.isEmpty()) {
                user.setPassword(User.hash(password));
            }

            Session session = hibernate.getCurrentSession();

            Transaction transaction = session.beginTransaction();
            user = (User) session.merge(user);

            if (create) {
                ied.applyHashMap((Map<String, Object>) data.get("ied"));
                ied.setUser(user);

                session.merge(ied);
            }

            session.flush();
            transaction.commit();

            String message = create ? "User successfully created" : "User successfully updated";

            flashManager.addFlash(new FlashMessage(message, "success", "check"));

            return this.redirectToRoute("EmployeesController.edit", MapUtils.createHashMap(
                    entry("user", user.getId())
            ));
        }

        return new JTwigResponse("views/personal-details/form.twig", MapUtils.createHashMap(
                entry("user", user),
                entry("form", form.createView())
        ));
    }

    /**
     * Route for user listing / searching.
     *
     * @param request   current request
     * @param hibernate hibernate
     * @return response
     */
    @Route(path = "", methods = {Method.GET, Method.POST})
    @Security("is_granted('list_users')")
    public Object list(Request request, Hibernate hibernate)
    {
        Map<String, String[]> queryParameters = request.getParameterMap();

        // Failsafe defaults
        int perPage = 20;
        String query = queryParameters.getOrDefault("query", new String[]{""})[0];
        int page = Integer.parseInt(queryParameters.getOrDefault("page", new String[]{"1"})[0]);

        Session session = hibernate.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query userQuery;

        Query countQuery;

        // No query provided
        if (query.isEmpty()) {
            userQuery = session.createQuery("from User");
            countQuery = session.createQuery("select count(*) from User");
        } else {
            // Query provided -> filter results
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
            countQuery = session.createQuery("select count(*) from User where " + searchQuery)
                    .setParameter("query", "%" + query + "%");
        }

        userQuery
                .setFirstResult((page - 1) * perPage)
                .setMaxResults(perPage);

        List users = userQuery.list();

        // Compute pagination
        long usersCount = (long) countQuery.uniqueResult();
        long maxPages = (long) Math.ceil((double) usersCount / perPage);

        transaction.commit();

        return new JTwigResponse("views/personal-details/list.twig", MapUtils.createHashMap(
                entry("users", users),
                entry("query", query),
                entry("page", page),
                entry("max_pages", maxPages)
        ));
    }

    /**
     * Route for creating users.
     *
     * @param runtimeBag The runtimeBag itself.
     * @return response
     */
    @Route(path = "/create", methods = {Method.GET, Method.POST})
    @Security("is_granted('create_user')")
    public Object create(RuntimeBag runtimeBag)
    {
        User user = new User();

        return handle(user, true);
    }

    /**
     * Route for editing user's personal details.
     *
     * @param runtimeBag      The runtimeBag itself.
     * @param routeParameters the route's parameters
     * @return response
     */
    @Route(path = "/{user}/edit", methods = {Method.GET, Method.POST}, requirements = {
            @Requirement(name = "user", requirement = App.USER_REGEX)
    })
    @Security("is_granted('edit_user', user)")
    @ParameterResolver(name = "user", type = UserResolver.class)
    public Object edit(RuntimeBag runtimeBag, RouteParameters routeParameters)
    {
        User user = (User) routeParameters.get("user");

        return handle(user, false);
    }

    /**
     * Route for viewing user's personal details.
     *
     * @param routeParameters the route's parameters
     * @return response
     */
    @Route(path = "/{user}", requirements = {
            @Requirement(name = "user", requirement = App.USER_REGEX)
    })
    @Security("is_granted('view_user', user)")
    @ParameterResolver(name = "user", type = UserResolver.class)
    public Object view(RouteParameters routeParameters)
    {
        User user = (User) routeParameters.get("user");

        return new JTwigResponse("views/personal-details/view.twig", MapUtils.createHashMap(
                entry("user", user)
        ));
    }
}
