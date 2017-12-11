package Framework.Exception;

import Framework.Route;

public class RouteDuplicateException extends Exception
{
    public RouteDuplicateException(Route route)
    {
        super("Route already present: " + route);
    }
}
