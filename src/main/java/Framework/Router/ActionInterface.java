package Framework.Router;

import Framework.Container.Container;
import Framework.Exception.UnhandledParameterException;
import Framework.Server.HTTPSession;

@FunctionalInterface
public interface ActionInterface
{
    Response apply(Container c, HTTPSession s, Route r) throws UnhandledParameterException;
}
