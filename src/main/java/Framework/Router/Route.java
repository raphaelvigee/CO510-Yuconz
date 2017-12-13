package Framework.Router;

import Framework.Server.HTTPSession;
import Framework.Server.Method;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route
{
    private Method method;

    private Path path;

    private ActionInterface handler;

    public Route(Method method, String pathDeclaration, ActionInterface handler)
    {
        this.method = method;
        this.path = new Path(pathDeclaration);
        this.handler = handler;
    }

    public Method getMethod()
    {
        return method;
    }

    public Path getPath()
    {
        return path;
    }

    public ActionInterface getHandler()
    {
        return handler;
    }

    public RouteParameters getParameters(HTTPSession session)
    {
        Pattern r = Pattern.compile(path.pattern);

        Matcher m = r.matcher(session.getUri());

        RouteParameters parameterValues = new RouteParameters();

        if (m.matches()) {
            path.parameters.forEach((index, name) -> {
                parameterValues.put(name, m.group(index));
            });
        }

        return parameterValues;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s", getMethod(), getPath().declaration);
    }
}
