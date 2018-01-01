package Framework.Authentication;

import Framework.Container.Container;
import Framework.Server.RuntimeBag;

@FunctionalInterface
public interface SecurityValidator
{
    boolean test(Container container, UserInterface user, RuntimeBag runtimeBag);
}
