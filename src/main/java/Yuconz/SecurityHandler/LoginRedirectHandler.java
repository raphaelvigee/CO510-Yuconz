package Yuconz.SecurityHandler;

import com.sallyf.sallyf.Authentication.SecurityDeniedHandler;
import com.sallyf.sallyf.Container.Container;
import com.sallyf.sallyf.Router.RedirectResponse;
import com.sallyf.sallyf.Router.URLGenerator;
import com.sallyf.sallyf.Server.RuntimeBag;
import com.sallyf.sallyf.Server.Status;

public class LoginRedirectHandler implements SecurityDeniedHandler
{
    @Override
    public Object apply(Container container, RuntimeBag runtimeBag)
    {
        URLGenerator urlGenerator = container.get(URLGenerator.class);

        String url = urlGenerator.url("AuthenticationController.login");

        return new RedirectResponse(url, Status.MOVED_TEMPORARILY);
    }
}
