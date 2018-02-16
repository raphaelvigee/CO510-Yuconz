package Yuconz.Controller;

import Yuconz.Manager.YuconzAuthenticationManager;
import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Authentication.UserInterface;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.Server.RuntimeBag;

public class AppController extends BaseController
{
    @Route(path = "/")
    public String index(RuntimeBag runtimeBag, YuconzAuthenticationManager authenticationManager)
    {
        UserInterface user = authenticationManager.getUser(runtimeBag);

        if (user == null) {
            return "Anonymous";
        }

        return user.toString();
    }
}
