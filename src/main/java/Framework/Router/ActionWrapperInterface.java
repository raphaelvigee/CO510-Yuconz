package Framework.Router;

import Framework.Exception.UnhandledParameterException;
import org.eclipse.jetty.server.Request;

@FunctionalInterface
public interface ActionWrapperInterface
{
    Response apply(Request request, Route route) throws UnhandledParameterException;
}
