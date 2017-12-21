package Framework.Router;

import Framework.Server.RuntimeBag;

public interface ActionParameterResolverInterface<R>
{
    boolean supports(Class parameterType, RuntimeBag runtimeBag);

    R resolve(Class parameterType, RuntimeBag runtimeBag);
}
