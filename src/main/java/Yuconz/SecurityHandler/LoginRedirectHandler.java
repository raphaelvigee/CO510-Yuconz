package Yuconz.SecurityHandler;

import com.sallyf.sallyf.Authentication.SecurityDeniedHandler;
import com.sallyf.sallyf.Container.Container;
import com.sallyf.sallyf.Exception.FrameworkException;
import com.sallyf.sallyf.Router.RedirectResponse;
import com.sallyf.sallyf.Router.URLGenerator;
import com.sallyf.sallyf.Server.RuntimeBag;
import com.sallyf.sallyf.Server.RuntimeBagContext;
import com.sallyf.sallyf.Server.Status;
import org.eclipse.jetty.server.Request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Handler for Login Redirection.
 */
public class LoginRedirectHandler implements SecurityDeniedHandler
{
    /**
     * Execute a login redirection.
     *
     * @param container the current container
     * @return object
     */
    @Override
    public Object apply(Container container)
    {
        RuntimeBag runtimeBag = RuntimeBagContext.get();

        URLGenerator urlGenerator = container.get(URLGenerator.class);

        String loginUrl = urlGenerator.url("AuthenticationController.login");

        // Add current URL as a get parameter for post login redirection
        try {
            loginUrl += "?next=" + URLEncoder.encode(runtimeBag.getRequest().getRequestURL().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new FrameworkException(e);
        }

        return new RedirectResponse(loginUrl, Status.MOVED_TEMPORARILY);
    }
}
