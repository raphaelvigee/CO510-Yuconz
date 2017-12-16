package Framework.Router;

import Framework.Exception.UnhandledParameterException;
import Framework.Server.Request;

@FunctionalInterface
public interface ActionWrapperInterface
{
    Response apply(Request r) throws UnhandledParameterException;
}
