package Yuconz.Controller;

import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Router.Response;
import com.sallyf.sallyf.Server.RuntimeBag;

public class AppController extends BaseController
{
    @Route(path = "/")
    public Response index(RuntimeBag runtimeBag, YuconzAuthenticationManager authenticationManager)
    {
        UserInterface user = authenticationManager.getUser(runtimeBag);

        if (user == null) {
            return redirectToRoute("AuthenticationController.login");
        }

        return redirectToRoute("DashboardController.index");
    }
}
