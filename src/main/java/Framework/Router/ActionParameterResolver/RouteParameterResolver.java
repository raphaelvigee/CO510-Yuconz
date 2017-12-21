package Framework.Router.ActionParameterResolver;

import Framework.Container.Container;
import Framework.Router.ActionParameterResolverInterface;
import Framework.Router.RouteParameters;
import Framework.Router.Router;
import Framework.Server.RuntimeBag;

public class RouteParameterResolver implements ActionParameterResolverInterface
{
    private Container container;

    public RouteParameterResolver(Container container)
    {
        this.container = container;
    }

    @Override
    public boolean supports(Class parameterType, RuntimeBag runtimeBag)
    {
        return parameterType == RouteParameters.class;
    }

    @Override
    public Object resolve(Class parameterType, RuntimeBag runtimeBag)
    {
        return container.get(Router.class).getRouteParameters(runtimeBag);
    }
}
