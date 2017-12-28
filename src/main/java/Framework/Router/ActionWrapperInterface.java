package Framework.Router;

import Framework.Exception.UnhandledParameterException;
import Framework.Server.RuntimeBag;

@FunctionalInterface
public interface ActionWrapperInterface
{
    Object apply(RuntimeBag runtimeBag) throws UnhandledParameterException;
}
