package Framework.Authentication.Voter;

import Framework.Authentication.UserInterface;
import Framework.Container.Container;
import Framework.Server.RuntimeBag;

@FunctionalInterface
public interface VoterInterface
{
    boolean test(Container container, UserInterface user, RuntimeBag runtimeBag);
}
