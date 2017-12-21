package Framework.Router.ActionParameterResolver;

import Framework.Router.ActionParameterResolverInterface;
import Framework.Server.RuntimeBag;
import org.eclipse.jetty.server.Request;

public class RequestResolver implements ActionParameterResolverInterface
{
    @Override
    public boolean supports(Class parameterType, RuntimeBag runtimeBag)
    {
        return parameterType == Request.class;
    }

    @Override
    public Object resolve(Class parameterType, RuntimeBag runtimeBag)
    {
        return runtimeBag.getRequest();
    }
}
