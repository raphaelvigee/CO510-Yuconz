package Framework.Router.ActionParameterResolver;

import Framework.Container.Container;
import Framework.Container.ContainerAwareInterface;
import Framework.Router.ActionParameterResolverInterface;
import Framework.Server.RuntimeBag;

public class ServiceResolver implements ActionParameterResolverInterface
{
    private Container container;

    public ServiceResolver(Container container)
    {
        this.container = container;
    }

    @Override
    public boolean supports(Class parameterType, RuntimeBag runtimeBag)
    {
        if (ContainerAwareInterface.class.isAssignableFrom(parameterType))
            if (container.has(parameterType))
                return true;
        return false;
    }

    @Override
    public Object resolve(Class parameterType, RuntimeBag runtimeBag)
    {
        return container.get(parameterType);
    }
}
