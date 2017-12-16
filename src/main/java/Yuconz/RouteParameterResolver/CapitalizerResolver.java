package Yuconz.RouteParameterResolver;

import Framework.Router.RouteParameterResolverInterface;
import Framework.Server.Request;

import java.util.Objects;

public class CapitalizerResolver implements RouteParameterResolverInterface<String>
{
    @Override
    public boolean supports(String name, String value, Request request)
    {
        return Objects.equals(name, "name");
    }

    @Override
    public String resolve(String name, String value, Request request)
    {
        return value.toUpperCase();
    }
}
