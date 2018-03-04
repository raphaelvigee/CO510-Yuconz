package Yuconz.JTwigFunction;

import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import com.sallyf.sallyf.Server.RuntimeBag;
import org.jtwig.functions.FunctionRequest;

import javax.servlet.http.HttpSession;

/**
 * JTwig function for fetching the of the current page.
 */
public class ActivePageFunction implements JTwigServiceFunction
{
    /**
     * Name of function.
     * @return name of function.
     */
    @Override
    public String name()
    {
        return "activePage";
    }

    /**
     * Execute function.
     * @param request page request
     * @return the active page
     */
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
