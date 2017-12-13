package Framework.Router;

import Framework.Exception.UnhandledParameterException;
import Framework.Server.HTTPSession;

@FunctionalInterface
public interface ActionWrapperInterface
{
    Response apply(HTTPSession s) throws UnhandledParameterException;
}
