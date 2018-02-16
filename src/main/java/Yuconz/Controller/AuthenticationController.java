package Yuconz.Controller;

import com.sallyf.sallyf.Annotation.Route;
import com.sallyf.sallyf.Controller.BaseController;
import com.sallyf.sallyf.FreeMarker.FreeMarkerResponse;
import com.sallyf.sallyf.Server.Method;

import java.util.HashMap;

@Route(path = "/auth")
public class AuthenticationController extends BaseController
{
    @Route(path = "/login", method = Method.POST)
    public FreeMarkerResponse loginPost()
    {
        return login();
    }

    @Route(path = "/login")
    public FreeMarkerResponse login()
    {
        return new FreeMarkerResponse("login.ftl", new HashMap<>());
    }
}
