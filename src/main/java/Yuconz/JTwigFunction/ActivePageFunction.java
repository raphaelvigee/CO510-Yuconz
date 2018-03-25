package Yuconz.JTwigFunction;

import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import com.sallyf.sallyf.Server.RuntimeBag;
import com.sallyf.sallyf.Server.RuntimeBagContext;
import org.jtwig.functions.FunctionRequest;

/**
 * JTwig function for fetching the of the current page.
 */
public class ActivePageFunction implements JTwigServiceFunction
{
    /**
     * Name of function.
     *
     * @return name of function.
     */
    @Override
    public String name()
    {
        return "activePage";
    }

    /**
     * Execute function.
     *
     * @param request page request
     * @return the active page
     */
    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);

        String expected = (String) request.get(0);

        RuntimeBag runtimeBag = RuntimeBagContext.get();

        return runtimeBag.getRoute().getName().equals(expected) ? "selected" : "";
    }
}
