package Yuconz.RouteParameterResolver;

import Framework.Router.Route;
import Framework.Router.RouteParameterResolverInterface;
import org.eclipse.jetty.server.Request;

import java.util.Objects;

public class CapitalizerResolver implements RouteParameterResolverInterface<String>
{
    @Override
    public boolean supports(String name, String value, Request request, Route route)
    {
        return Objects.equals(name, "name");
    }

    @Override
    public String resolve(String name, String value, Request request, Route route)
    {
        return value.toUpperCase();
    }
}
