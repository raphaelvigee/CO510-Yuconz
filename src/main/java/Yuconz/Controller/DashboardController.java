package Yuconz.Controller;

import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.Annotation.Security;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.JTwig.JTwigResponse;

@Route(path = "/dashboard")
public class DashboardController extends BaseController
{
    @Route(path = "")
    @Security("is_granted($, 'authenticated')")
    public JTwigResponse index()
    {
        return new JTwigResponse("views/dashboard.twig");
    }
}
