package Framework.Routing;

import Framework.Container.Container;
import Framework.Exception.UnhandledParameterException;

public interface ActionInterface
{
    Response apply(Container c, HTTPSession s, Route r) throws UnhandledParameterException;
}
