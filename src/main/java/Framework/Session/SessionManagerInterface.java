package Framework.Session;

import Framework.Container.ContainerAwareInterface;
import Framework.Server.Request;

public interface SessionManagerInterface<S> extends ContainerAwareInterface
{
    S getSession(Request request);

    S getSession(String sessionId);

    S createSession(Request request);
}
