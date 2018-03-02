package Yuconz.JTwigFunction;

import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import com.sallyf.sallyf.Server.RuntimeBag;
import org.jtwig.functions.FunctionRequest;

import javax.servlet.http.HttpSession;

public class ActivePageFunction implements JTwigServiceFunction
{
    @Override
    public String name()
    {
        return "activePage";
    }

    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(2);
        request.maximumNumberOfArguments(2);

        RuntimeBag runtimeBag = (RuntimeBag) request.get(0);
        String expected = (String) request.get(1);

        return runtimeBag.getRoute().getName().equals(expected) ? "selected" : "";
    }
}
