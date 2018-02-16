package Yuconz.Controller;

import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.FreeMarker.FreeMarkerResponse;

import java.util.HashMap;

@Route(path = "/dashboard")
public class DashboardController extends BaseController
{

    @Route(path = "")
    @Security("is_granted($, 'authenticated')")
    public FreeMarkerResponse index()
    {
        return new FreeMarkerResponse("dashboard.ftl", new HashMap<>());
    }
}
