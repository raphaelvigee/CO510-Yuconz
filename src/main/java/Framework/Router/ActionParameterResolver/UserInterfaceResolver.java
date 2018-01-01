package Framework.Router.ActionParameterResolver;

import Framework.Authentication.AuthenticationManager;
import Framework.Authentication.UserInterface;
import Framework.Container.Container;
import Framework.Router.ActionParameterResolverInterface;
import Framework.Server.RuntimeBag;

public class UserInterfaceResolver implements ActionParameterResolverInterface
{
    private Container container;

    public UserInterfaceResolver(Container container)
    {
        this.container = container;
    }

    @Override
    public boolean supports(Class parameterType, RuntimeBag runtimeBag)
    {
        return UserInterface.class.isAssignableFrom(parameterType) && container.has(AuthenticationManager.class);
    }

    @Override
    public Object resolve(Class parameterType, RuntimeBag runtimeBag)
    {
        AuthenticationManager authenticationManager = container.get(AuthenticationManager.class);

        return authenticationManager.getUser(runtimeBag);
    }
}
