package Framework.Router;

import Framework.Server.Request;

public interface RouteParameterResolverInterface<R>
{
    boolean supports(String name, String value, Request request);

    R resolve(String name, String value, Request request);
}
