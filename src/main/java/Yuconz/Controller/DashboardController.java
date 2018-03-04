package Yuconz.Controller;

import Yuconz.SecurityHandler.LoginRedirectHandler;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.JTwig.JTwigResponse;

/**
 * Controller for system dashboard.
 */
@Route(path = "/dashboard")
@Security(value = "is_granted($, 'authenticated')", handler = LoginRedirectHandler.class)
public class DashboardController extends BaseController
{
    /**
     * Display main dashboard from Twig.
     * @return response
     */
    @Route(path = "")
    public JTwigResponse index()
    {
        return new JTwigResponse("views/dashboard.twig");
    }
}
