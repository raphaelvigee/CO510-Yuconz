package Framework.Exception;

import Framework.Route;

public class RouteDuplicateException extends FrameworkException
{
    public RouteDuplicateException(Route route)
    {
        super("Route already present: " + route);
    }
}
