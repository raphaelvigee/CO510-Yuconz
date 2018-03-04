package Yuconz.JTwigFunction;

import com.sallyf.sallyf.JTwig.JTwigServiceFunction;
import org.jtwig.functions.FunctionRequest;

import java.util.stream.IntStream;

/**
 * JTwig function for generating an array from a range
 */
public class RangeFunction implements JTwigServiceFunction
{
    /**
     * Name of function.
     * @return name of function.
     */
    @Override
    public String name()
    {
        return "range";
    }

    /**
     * Execute function.
     * @param request page request
     * @return the requested range array
     */
    @Override
    public Object execute(FunctionRequest request)
    {
        request.minimumNumberOfArguments(2);
        request.maximumNumberOfArguments(2);

        Number from = (Number) request.get(0);
        Number to = (Number) request.get(1);

        return IntStream.rangeClosed(from.intValue(), to.intValue()).boxed().toArray();
    }
}
