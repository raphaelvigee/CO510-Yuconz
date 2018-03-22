package Yuconz.Controller;

import Yuconz.App;
import Yuconz.Entity.AbstractRecord;
import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Entity.User;
import Yuconz.Manager.RecordManager;
import Yuconz.ParameterResolver.RecordResolver;
import Yuconz.SecurityHandler.LoginRedirectHandler;
import com.sallyf.sallyf.Annotation.Requirement;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.JTwig.JTwigResponse;
import com.sallyf.sallyf.Router.ParameterResolver;
import com.sallyf.sallyf.Router.RouteParameters;
import com.sallyf.sallyf.Server.Method;
import com.sallyf.sallyf.Utils.MapUtils;

import java.util.HashMap;
import java.util.List;

import static com.sallyf.sallyf.Utils.MapUtils.entry;

/**
 * Controller for general records, handle all requests.
 */
@Security(value = "is_granted('authenticated')", handler = LoginRedirectHandler.class)
@Route(path = "/record")
public class RecordController extends BaseController
{
    /**
     * Route for listing all records for a user.
     *
     * @param recordManager system's record manager
     * @param user          user to fetch records for
     * @return response
     */
    @Route(path = "", methods = {Method.GET})
    @Security("is_granted('list_records')")
    public Object list(RecordManager recordManager, User user)
    {
        List<AbstractRecord> records = recordManager.getRecords(user);

        return new JTwigResponse("views/record/list.twig", new HashMap<String, Object>()
        {{
            put("records", records);
        }});
    }

    /**
     * Route for viewing a specific record for a user
     *
     * @param routeParameters route's parameters
     * @return response
     */
    @Route(path = "/{record}", methods = {Method.GET, Method.POST}, requirements = {
            @Requirement(name = "record", requirement = App.RECORD_REGEX)
    })
    @ParameterResolver(name = "record", type = RecordResolver.class)
    @Security("is_granted('view_record', record)")
    public Object view(RouteParameters routeParameters)
    {
        AbstractRecord record = (AbstractRecord) routeParameters.get("record");

        if (record instanceof AnnualReviewRecord) {
            return this.redirectToRoute("AnnualReviewController.view", new HashMap<String, String>()
            {{
                put("record", record.getId());
            }});
        }

        return new JTwigResponse("views/record/view.twig", MapUtils.createHashMap(
                entry("record", record)
        ));
    }
}
