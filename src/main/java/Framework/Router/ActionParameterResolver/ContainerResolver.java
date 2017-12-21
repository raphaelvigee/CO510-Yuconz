package Framework.Router.ActionParameterResolver;

import Framework.Container.Container;
import Framework.Router.ActionParameterResolverInterface;
import Framework.Server.RuntimeBag;

public class ContainerResolver implements ActionParameterResolverInterface
{
    private Container container;

    public ContainerResolver(Container container)
    {
        this.container = container;
    }

    @Override
    public boolean supports(Class parameterType, RuntimeBag runtimeBag)
    {
        return parameterType == Container.class;
    }

    @Override
    public Object resolve(Class parameterType, RuntimeBag runtimeBag)
    {
        return container;
    }
}
