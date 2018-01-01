package Framework.Authentication.Validator;

import Framework.Authentication.SecurityValidator;
import Framework.Authentication.UserInterface;
import Framework.Container.Container;
import Framework.Server.RuntimeBag;

public class LoggedInValidator implements SecurityValidator
{
    @Override
    public boolean test(Container container, UserInterface user, RuntimeBag runtimeBag)
    {
        return null != user;
    }
}
