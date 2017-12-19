package Yuconz.RouteParameterResolver;

import Framework.Router.RouteParameterResolverInterface;
import Framework.Server.RuntimeBag;

import java.util.Objects;

public class CapitalizerResolver implements RouteParameterResolverInterface<String>
{
    @Override
    public boolean supports(String name, String value, RuntimeBag runtimeBag)
    {
        return Objects.equals(name, "name");
    }

    @Override
    public String resolve(String name, String value, RuntimeBag runtimeBag)
    {
        return value.toUpperCase();
    }
}
