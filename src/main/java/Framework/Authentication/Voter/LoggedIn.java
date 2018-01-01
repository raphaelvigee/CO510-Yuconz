package Framework.Authentication.Voter;

import Framework.Authentication.UserInterface;
import Framework.Container.Container;
import Framework.Server.RuntimeBag;

public class LoggedIn implements VoterInterface
{
    @Override
    public boolean test(Container container, UserInterface user, RuntimeBag runtimeBag)
    {
        return null != user;
    }
}
