package Framework.Router;

import Framework.Server.RuntimeBag;

public interface RouteParameterResolverInterface<R>
{
    boolean supports(String name, String value, RuntimeBag runtimeBag);

    R resolve(String name, String value, RuntimeBag runtimeBag);
}
