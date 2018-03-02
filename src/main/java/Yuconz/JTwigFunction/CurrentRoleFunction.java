package Yuconz.JTwigFunction;

import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import com.sallyf.sallyf.Server.RuntimeBag;
import org.jtwig.functions.FunctionRequest;

import javax.servlet.http.HttpSession;

public class CurrentRoleFunction implements JTwigServiceFunction
{
    @Override
    public String name()
    {
        return "getRole";
    }

    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);

        RuntimeBag runtimeBag = (RuntimeBag) request.get(0);

        HttpSession session = runtimeBag.getRequest().getSession(true);

        return session.getAttribute("role");
    }
}
