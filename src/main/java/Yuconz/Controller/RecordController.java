package Yuconz.Controller;

import Yuconz.Entity.AbstractRecord;
import Yuconz.Main;
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

import static com.sallyf.sallyf.Utils.MapUtils.entry;

@Security(value = "is_granted('authenticated')", handler = LoginRedirectHandler.class)
@Route(path = "/record")
public class RecordController extends BaseController
{
    @Route(path = "/{record}", methods = {Method.GET, Method.POST}, requirements = {
            @Requirement(name = "record", requirement = Main.RECORD_REGEX)
    })
    @Security("is_granted('view_record', record)")
    @ParameterResolver(name = "record", type = RecordResolver.class)
    public Object view(RouteParameters routeParameters)
    {
        AbstractRecord record = (AbstractRecord) routeParameters.get("record");

        return new JTwigResponse("views/record/view.twig", MapUtils.createHashMap(
                entry("record", record)
        ));
    }
}
