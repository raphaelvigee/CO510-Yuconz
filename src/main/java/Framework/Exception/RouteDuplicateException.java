package Framework.Exception;

import Framework.Router.Route;

public class RouteDuplicateException extends FrameworkException
{
    public RouteDuplicateException(Route route)
    {
        super("Route already present: " + route);
    }
}
