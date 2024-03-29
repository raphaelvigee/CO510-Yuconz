package Yuconz.Controller;

import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Router.Response;

/**
 * App app controller, redirects to specific part of
 * site: auth controller or dashboard index.
 */
public class AppController extends BaseController
{
    /**
     * If user is logged, redirect to log in screen,
     * if not redirect to dashboard index.
     *
     * @param authenticationManager system authentication manager
     * @return response
     */
    @Route(path = "/")
    public Response index(YuconzAuthenticationManager authenticationManager)
    {
        UserInterface user = authenticationManager.getUser();

        if (user == null) {
            return redirectToRoute("AuthenticationController.login");
        }

        return redirectToRoute("DashboardController.index");
    }
}
