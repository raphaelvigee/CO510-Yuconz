package Yuconz.RouteParameterResolver;

import com.sallyf.sallyf.Router.RouteParameterResolverInterface;
import com.sallyf.sallyf.Server.RuntimeBag;

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
