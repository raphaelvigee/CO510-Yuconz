package Framework.Session.Handler;

import Framework.Container.ContainerAware;
import Framework.Server.Cookie;
import Framework.Server.Request;
import Framework.Session.SessionManagerInterface;
import Framework.Util.Random;
import fi.iki.elonen.NanoHTTPD;

import java.util.HashMap;

public class InMemorySessionManager extends ContainerAware implements SessionManagerInterface<InMemorySession>
{
    private HashMap<String, InMemorySession> sessions = new HashMap<>();

    public String getIdCookieName()
    {
        return "__SESSION_ID__";
    }

    public int getIdCookieExpire()
    {
        return 365;
    }

    public String getIdCookiePath()
    {
        return "/";
    }

    public String generateSessionId()
    {
        String id;

        do {
            id = Random.randomString(16);
        } while (getSession(id) != null);

        return id;
    }

    @Override
    public InMemorySession getSession(Request request)
    {
        NanoHTTPD.CookieHandler cookies = request.getCookies();

        String cookie = cookies.read(getIdCookieName());

        if (cookie == null) {
            return createSession(request);
        }

        InMemorySession session = getSession(cookie);

        if (session == null) {
            return createSession(request);
        }

        return session;
    }

    @Override
    public InMemorySession getSession(String sessionId)
    {
        return sessions.get(sessionId);
    }

    @Override
    public InMemorySession createSession(Request request)
    {
        String id = generateSessionId();
        InMemorySession session = new InMemorySession(id);
        sessions.put(id, session);

        request.getCookies().set(new Cookie(getIdCookieName(), id, getIdCookieExpire(), getIdCookiePath()));

        return session;
    }
}
